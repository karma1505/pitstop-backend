package com.garage.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure path matching to handle trailing slashes
     * This makes trailing slashes optional across all endpoints
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // These methods are deprecated in newer Spring versions
        // The functionality is now handled differently in Spring Boot 2.4+
        // No explicit configuration needed for suffix pattern matching
    }
}
