package com.edu.backend;

import com.edu.backend.config.DatabaseConfigLoggerInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Backend2Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Backend2Application.class);
        application.addInitializers(new DatabaseConfigLoggerInitializer());
        application.run(args);
    }

}
