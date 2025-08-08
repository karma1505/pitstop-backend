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
        // Disable suffix pattern matching to prevent conflicts
        configurer.setUseSuffixPatternMatch(false);
        configurer.setUseRegisteredSuffixPatternMatch(false);
    }
}
