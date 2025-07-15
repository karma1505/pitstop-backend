package com.garage.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GarageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GarageBackendApplication.class, args);
    }
} 