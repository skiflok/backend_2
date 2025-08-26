package com.edu.eventservice.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductUpdateListener {

    private final Sinks.Many<String> updateStockSseSink;
    private final Sinks.Many<String> updatePriceSseSink;

    @KafkaListener(
            topics = "${kafka.in.product.update-stock-event.topic}",
            groupId = "${kafka.in.product.update-stock-event.group-id}")
    public void updateProductStocks(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("Get message from [topic {}]", topic);
        log.debug("[message {}]", message);
        try {
            Sinks.EmitResult result = updateStockSseSink.tryEmitNext(message);
            if (result.isFailure()) {
                log.warn("Failed to emit message to updateStockSseSink: {}", result);
            }
        } catch (Exception e) {
            log.error("Error ", e);
        }
    }

    @KafkaListener(
            topics = "${kafka.in.product.update-price-event.topic}",
            groupId = "${kafka.in.product.update-price-event.group-id}")
    public void updateProductPrice(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("Get message from [topic {}]", topic);
        log.debug("[message {}]", message);
        try {
            Sinks.EmitResult result = updatePriceSseSink.tryEmitNext(message);
            if (result.isFailure()) {
                log.warn("Failed to emit message to updatePriceSseSink: {}", result);
            }
        } catch (Exception e) {
            log.error("Error ", e);
        }
    }
}

