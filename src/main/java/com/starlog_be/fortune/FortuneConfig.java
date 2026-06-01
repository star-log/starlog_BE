package com.starlog_be.fortune;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fortune")
public record FortuneConfig(
        @Value("${fortune.fortune-url}") String fortuneUrl,
        @Value("${fortune.timeout}") int timeout,
        @Value("${fortune.user-agent}") String userAgent
) {
}
