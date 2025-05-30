package com.edu.eventservice.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private final Sinks.Many<String> sseSink;

    @KafkaListener(
            topics = "${kafka.in.product.update-event.topic}",
            groupId = "${kafka.in.product.update-event.group-id}")
    public void updateProductStocks(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("Get message from [topic {}]", topic);
        log.debug("[message {}]", message);
        try {
            Sinks.EmitResult result = sseSink.tryEmitNext(message);
            if (result.isFailure()) {
                log.warn("Failed to emit message to SSE sink: {}", result);
            }
        } catch (Exception e) {
            log.error("Error ", e);
        }
    }
}

