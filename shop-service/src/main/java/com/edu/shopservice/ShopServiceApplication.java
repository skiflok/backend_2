package com.edu.shopservice;

import com.edu.shopservice.config.DatabaseConfigLoggerInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ShopServiceApplication.class);
        application.addInitializers(new DatabaseConfigLoggerInitializer());
        application.run(args);
    }

}
