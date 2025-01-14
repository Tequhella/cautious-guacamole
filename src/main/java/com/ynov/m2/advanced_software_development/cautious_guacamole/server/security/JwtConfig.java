package com.ynov.m2.advanced_software_development.cautious_guacamole.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}