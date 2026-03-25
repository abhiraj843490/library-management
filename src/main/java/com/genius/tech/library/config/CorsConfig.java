package com.genius.tech.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    public CorsConfig() {
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(new String[]{this.allowedOrigins})
                .allowedMethods(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"})
                .allowedHeaders(new String[]{"*"});
    }
}