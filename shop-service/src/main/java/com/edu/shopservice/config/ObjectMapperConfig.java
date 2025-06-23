package com.edu.shopservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ObjectMapperConfig {

//    @Bean
//    ObjectMapper defaultObjectMapper () {
//        return new ObjectMapper();
//    }

    @Bean
//    @Qualifier("defaultObjectMapper")
    public ObjectMapper defaultObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder
                .modules(new JavaTimeModule())
//                .featuresToEnable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .build();
    }
}
