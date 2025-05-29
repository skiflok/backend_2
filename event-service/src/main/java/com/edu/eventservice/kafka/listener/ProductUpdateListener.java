package com.edu.eventservice.kafka.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductUpdateListener {


//    @KafkaListener(
//            topics = "${kafka.in.product.product-update-event.topic}",
//            groupId = "${kafka.in.product.product-update-event.group-id}")
    public void updateProductStocks(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("Get message from [topic {}]", topic);
        log.debug("[message {}]", message);
        try {
//            ProductUpdateEvent event = defaultObjectMapper.readValue(message, ProductUpdateEvent.class);
//            productService.updateStock(event.getProductId(), event.getNewStock());
//        } catch (JsonProcessingException e) {
//            log.error("mapping error", e);
        } catch (Exception e) {
            log.error("Error ", e);
        }
    }
}
