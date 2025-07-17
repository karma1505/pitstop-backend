package com.garage.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.garage.backend.repository")
public class GarageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GarageBackendApplication.class, args);
    }
} 