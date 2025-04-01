package com.edu.eventservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventGeneratorService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private int i = 0;

//    @PostConstruct
    @Scheduled(fixedRate = 5000) // Запуск каждые 5 секунд
    private void generate() {
        sendMessage("hello " + i++);
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("test", message);
        log.info("Сообщение отправлено: {}", message);
    }
}
