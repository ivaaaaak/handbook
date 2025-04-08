package com.example.handbook.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private String warmupUrl;
    private String warmupAPath;
    private String warmupBPath;

    private int warmupThreadPool;
    private int notifyThreadPool;
    private int aSaveTtThreadPool;
    private int bSaveTtThreadPool;
    private int notifySaveTtThreadPool;

    private int numOfEntriesToMainCache;
    private int ttSavePause;

    private int timeoutBetweenRequests;
    private int rqDurationStartRange;
    private int rqDurationEndRange;
}
