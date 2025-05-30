package com.edu.eventservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequiredArgsConstructor
public class SseController {

    private final Sinks.Many<String> sseSink;

    @GetMapping(value = "/product-update-event", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamKafkaEvents() {
        return sseSink.asFlux();
    }
}


