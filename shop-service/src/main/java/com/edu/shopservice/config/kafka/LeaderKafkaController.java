package com.edu.shopservice.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaderKafkaController {
    private final KafkaListenerEndpointRegistry registry;

    public void startListeners() {
        start("product-stock-listener");
        start("product-price-listener");
    }

    public void stopListeners() {
        stop("product-stock-listener");
        stop("product-price-listener");
    }

    public void start(String listenerId) {
        var container = registry.getListenerContainer(listenerId);
        if (container == null) {
            log.warn("Listener with id {} not found in registry", listenerId);
            return;
        }
        if (!container.isRunning()) {
            log.info("Starting Kafka listener: {}", listenerId);
            container.start();
        } else {
            log.info("Kafka listener {} already running", listenerId);
        }
    }


    private void stop(String listenerId) {
        var container = registry.getListenerContainer(listenerId);
        if (container != null && container.isRunning()) {
            log.info("Stopping Kafka listener: {}", listenerId);
            container.stop();
        }
    }
}

