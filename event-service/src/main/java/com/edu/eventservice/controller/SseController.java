package com.edu.eventservice.controller;

import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
public class SseController {

    // Используем Sinks.Replay для хранения только последнего сообщения
    private final Sinks.Many<String> sink = Sinks.many().replay().limit(1);

    @GetMapping(value = "/kafka-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamKafkaEvents() {
        return sink.asFlux();
    }

    @KafkaListener(topics = "kafka-sse", groupId = "kafka-sse-group-id")
    public void listen(String message) {
        // Отправляем только последнее сообщение
        sink.tryEmitNext(message);
    }
}

