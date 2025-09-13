package com.edu.shopservice.service.curator;

import com.edu.shopservice.config.kafka.LeaderKafkaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class LeaderService extends LeaderSelectorListenerAdapter {
    private final CuratorFramework client;
    private LeaderSelector selector; // lazy init
    private final String instanceId = UUID.randomUUID().toString();
    private final AtomicBoolean isLeader = new AtomicBoolean(false);
    private final String basePath;
    private final LeaderKafkaController leaderKafkaController;

    public LeaderService(
            CuratorFramework client,
            @Value("${zookeeper.base-path}") String basePath,
            LeaderKafkaController leaderKafkaController
    ) {
        this.client = client;
        this.basePath = basePath;
        this.leaderKafkaController = leaderKafkaController;
        // don't start selector here — wait until ApplicationReadyEvent
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startLeaderSelector() {
        try {
            // init and start selector after app is ready (all beans registered)
            this.selector = new LeaderSelector(client, basePath + "/leader", this);
            this.selector.autoRequeue();
            this.selector.start();

            // register ephemeral info znode
            registerInstance();

            log.info("LeaderSelector started for instanceId={}", instanceId);
        } catch (Exception e) {
            log.error("Failed to start LeaderSelector", e);
        }
    }

    private void registerInstance() {
        try {
            String nodePath = basePath + "/instances/" + instanceId;
            String payload = String.format("{\"id\":\"%s\",\"host\":\"%s\",\"port\":%s}", instanceId,
                    InetAddress.getLocalHost().getHostAddress(), System.getProperty("server.port", "9999"));
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(nodePath, payload.getBytes(StandardCharsets.UTF_8));
            log.info("Registered instance node: {}", nodePath);
        } catch (Exception e) {
            log.error("Error registering instance in ZooKeeper", e);
        }
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        // called when this instance becomes leader
        try {
            isLeader.set(true);

            // create/update /basePath/currentLeader znode
            String leaderPath = basePath + "/currentLeader";
            String payload = instanceId; // или host:port
            if (client.checkExists().forPath(leaderPath) != null) {
                client.setData().forPath(leaderPath, payload.getBytes(StandardCharsets.UTF_8));
            } else {
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(leaderPath, payload.getBytes(StandardCharsets.UTF_8));
            }

            log.info("I am leader now: {}", instanceId);
            // enable writers, start kafka listeners etc.
            onBecomingLeader();

            // hold leadership until interrupted
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // restore interrupted flag
                log.info("Leadership interrupted for instance {}", instanceId);
            }
        } finally {
            // cleanup when losing leadership
            isLeader.set(false);
            onRelinquishLeadership();
            log.info("Relinquished leadership: {}", instanceId);
        }
    }

    private void onBecomingLeader() {
        leaderKafkaController.startListeners();
        log.info("onBecomingLeader - started kafka listeners");
    }

    private void onRelinquishLeadership() {
        leaderKafkaController.stopListeners();
        log.info("onRelinquishLeadership - stopped kafka listeners");
    }

    public boolean isLeader() {
        return isLeader.get();
    }

    @PreDestroy
    public void stop() {
        if (selector != null) {
            try {
                selector.close();
            } catch (Exception e) {
                log.warn("Error closing LeaderSelector", e);
            }
        }
        // don't close client here — CuratorFramework bean has its own lifecycle management
        log.info("LeaderService stopped for instance {}", instanceId);
    }
}
