package com.edu.authservise;

import com.edu.authservise.config.DatabaseConfigLoggerInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AuthServiceApplication.class);
        application.addInitializers(new DatabaseConfigLoggerInitializer());
        application.run(args);
    }
}
