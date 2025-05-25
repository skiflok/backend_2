package com.edu.shopservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductTopicListener {

    @KafkaListener(
            topics = "${kafka.in.product.updates-stocks.topic",
            groupId = "kafka.in.product.updates-stocks.group-id")
    public void listenTestTopic(String message) {
        log.info("Получено сообщение из топика 'product-updates-stocks': {}", message);
    }
}
