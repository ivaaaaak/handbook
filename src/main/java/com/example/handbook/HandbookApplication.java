package com.example.handbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.example.handbook.config.ApplicationProperties;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class HandbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandbookApplication.class, args);
    }

}
