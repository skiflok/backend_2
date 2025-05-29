package com.edu.eventservice.controller;

import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class SseController {

    // 1. Reactive WebFlux + Reactor Sinks
    private final Sinks.Many<String> sink = Sinks.many().replay().limit(1);

    @GetMapping(value = "/kafka-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamKafkaEvents() {
        return sink.asFlux();
    }

    // 2. Classic Spring MVC + SseEmitter
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/kafka-sse-emitter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamWithEmitter() {
        // no timeout: 0L
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);

        // remove emitter on completion / timeout / error
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        return emitter;
    }

    @KafkaListener(topics = "kafka-sse", groupId = "kafka-sse-group-id")
    public void listen(String message) {
        // a) send into reactive sink
        sink.tryEmitNext(message);

        // b) send to all classic emitters
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(message));
            } catch (IOException ex) {
                emitters.remove(emitter);
            }
        }
    }
}


