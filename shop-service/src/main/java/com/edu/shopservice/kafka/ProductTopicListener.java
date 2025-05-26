package com.edu.shopservice.kafka;

import com.edu.shopservice.dto.event.ProductUpdateEvent;
import com.edu.shopservice.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductTopicListener {
    private final ProductService productService;
    private final ObjectMapper defaultObjectMapper;

    @KafkaListener(
            topics = "${kafka.in.product.updates-stocks.topic}",
            groupId = "${kafka.in.product.updates-stocks.group-id}")
    public void listenTestTopic(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        //todo логика изменения количества товара
        log.info("Get message from [topic {}]", topic);
        log.debug("[message {}]", message);
        try {
            ProductUpdateEvent event = defaultObjectMapper.readValue(message, ProductUpdateEvent.class);
            productService.updateStock(event.getProductId(), event.getNewStock());
        } catch (JsonProcessingException e) {
            log.error("mapping error", e);
        } catch (Exception e) {
            log.error("Error ", e);
        }
    }
}
