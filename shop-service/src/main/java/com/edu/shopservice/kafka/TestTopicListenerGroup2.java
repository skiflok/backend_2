package com.edu.shopservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestTopicListenerGroup2 {

    @KafkaListener(topics = "test", groupId = "test-group2")
    public void listenTestTopic(String message) {
        log.info("[consumer 3] Получено сообщение из топика 'test': {}", message);
    }
}
