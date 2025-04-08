package com.example.handbook.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "tarantool")
public class TarantoolProperties {

    private String username;
    private String password;
    private List<String> url;
}
