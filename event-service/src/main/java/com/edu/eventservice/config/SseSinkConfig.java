package com.edu.eventservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class SseSinkConfig {

    @Bean
    public Sinks.Many<String> updateStockSseSink() {
        // multicast + directBestEffort: для каждого нового сообщения
        // пытаемся дёрнуть каждого подписчика сразу, без буфера
        return Sinks.many().multicast().directBestEffort();
    }

    @Bean
    public Sinks.Many<String> updatePriceSseSink() {
        return Sinks.many().multicast().directBestEffort();
    }

}
