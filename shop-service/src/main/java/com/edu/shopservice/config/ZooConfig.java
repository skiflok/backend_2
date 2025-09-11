package com.edu.shopservice.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZooConfig {
    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework curatorFramework(@Value("${zookeeper.connect-string}") String zkConn) {
        return CuratorFrameworkFactory.builder()
                .connectString(zkConn)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
    }
}
