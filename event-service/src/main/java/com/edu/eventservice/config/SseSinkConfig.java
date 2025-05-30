package com.edu.eventservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class SseSinkConfig {

    @Bean
    public Sinks.Many<String> sseSink() {
        return Sinks.many().replay().limit(1);
    }
}
