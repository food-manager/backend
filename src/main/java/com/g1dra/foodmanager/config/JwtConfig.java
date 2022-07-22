package com.g1dra.foodmanager.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@Configuration
@Data
public class JwtConfig {
    private Long tokenValidity;
    private String authoritiesKey;
    private String secret;
}
