package com.starlog_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StarlogBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarlogBeApplication.class, args);
	}

}
