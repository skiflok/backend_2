package com.edu.eventservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")          // на все пути
                .allowedOrigins("*")        // или конкретные домены
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowedHeaders("*");
    }
}
