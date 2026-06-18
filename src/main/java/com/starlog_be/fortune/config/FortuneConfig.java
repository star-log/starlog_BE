package com.starlog_be.fortune.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fortune")
public record FortuneConfig(
        String prompt,
        String apiKey,
        String model
) {
}
