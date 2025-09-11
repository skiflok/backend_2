package com.edu.shopservice.service.curator;

import com.edu.shopservice.config.kafka.LeaderKafkaController;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
//@DependsOn("kafkaListenerEndpointRegistry")
@Component
public class LeaderService extends LeaderSelectorListenerAdapter {
    private final CuratorFramework client;
    private final LeaderSelector selector;
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
        this.selector = new LeaderSelector(client, basePath + "/leader", this);
        this.selector.autoRequeue();
        this.selector.start();

        // register ephemeral info znode
        registerInstance();
    }

    private void registerInstance() {
        try {
            String nodePath = basePath + "/instances/" + instanceId;
            String payload = String.format("{\"id\":\"%s\",\"host\":\"%s\",\"port\":%s}", instanceId,
                    InetAddress.getLocalHost().getHostAddress(), System.getProperty("server.port", "9999"));
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(nodePath, payload.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            // логировать
            log.error("Error ", e);
        }
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        // called when this instance becomes leader
        try {
            isLeader.set(true);
            // optional: create/update /basePath/currentLeader znode
            String leaderPath = basePath + "/currentLeader";
            String payload = instanceId; // или host:port
            if (client.checkExists().forPath(leaderPath) != null) {
                client.setData().forPath(leaderPath, payload.getBytes(StandardCharsets.UTF_8));
            } else {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(leaderPath, payload.getBytes(StandardCharsets.UTF_8));
            }

            // do leader work: enable writers, start kafka listeners etc.
            onBecomingLeader();
            // stay leader while JVM up and until interrupted
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException ie) {
            // we lost leadership or shutdown requested
            log.error("Error ", ie);
        } finally {
            // cleanup when losing leadership
            isLeader.set(false);
            onRelinquishLeadership();
        }
    }

    private void onBecomingLeader() {
        // hook: notify other beans (via ApplicationEvent or call they expose)
        leaderKafkaController.startListeners();
        log.info("onBecomingLeader");
    }

    private void onRelinquishLeadership() {
        // hook: notify other beans
        leaderKafkaController.stopListeners();
        log.info("onRelinquishLeadership");
    }

    public boolean isLeader() {
        return isLeader.get();
    }

    @PreDestroy
    public void stop() {
        selector.close();
        client.close();
    }
}
