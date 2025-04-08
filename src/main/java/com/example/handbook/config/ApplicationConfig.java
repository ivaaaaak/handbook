package com.example.handbook.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ApplicationConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Bean
    public Executor threadPoolForWarmupCompletedCheck() {
        int maxPoolSize = 1;
        int queueCapacity = 1;
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("notifies-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor warmupThreadPool(ApplicationProperties applicationProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getWarmupThreadPool());
        executor.setMaxPoolSize(applicationProperties.getWarmupThreadPool());
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("warmup-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor notifyThreadPool(ApplicationProperties applicationProperties) {
        return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(applicationProperties.getNotifyThreadPool()));
    }

    @Bean
    public Executor saveAThreadPool(ApplicationProperties applicationProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getASaveTtThreadPool());
        executor.setMaxPoolSize(applicationProperties.getASaveTtThreadPool());
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("A-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor saveBThreadPool(ApplicationProperties applicationProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getBSaveTtThreadPool());
        executor.setMaxPoolSize(applicationProperties.getBSaveTtThreadPool());
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("B-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor processNotifyThreadPool(ApplicationProperties applicationProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getNotifySaveTtThreadPool());
        executor.setMaxPoolSize(applicationProperties.getNotifySaveTtThreadPool());
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("notifyTP-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
