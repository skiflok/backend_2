package com.edu.shopservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class DatabaseConfigLoggerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String databaseUrl = applicationContext.getEnvironment().getProperty("spring.datasource.url");
        String databaseUsername = applicationContext.getEnvironment().getProperty("spring.datasource.username");
//        String databasePassword = applicationContext.getEnvironment().getProperty("spring.datasource.password");

        // Логируем параметры подключения
        log.info("Подключение к базе данных:");
        log.info("URL: {}", databaseUrl);
        log.info("Пользователь: {}", databaseUsername);
    }
}
