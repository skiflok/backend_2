package com.edu.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class DatabaseConnectionLogger implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String databaseUrl = applicationContext.getEnvironment().getProperty("spring.datasource.url");
        String databaseUsername = applicationContext.getEnvironment().getProperty("spring.datasource.username");
        String databasePassword = applicationContext.getEnvironment().getProperty("spring.datasource.password");

        // Логируем параметры подключения
        log.info("Подключение к базе данных:");
        log.info("URL: {}", databaseUrl);
        log.info("Пользователь: {}", databaseUsername);
        log.info("Пароль: {}", "*****"); // Не показывайте пароль
    }
}
