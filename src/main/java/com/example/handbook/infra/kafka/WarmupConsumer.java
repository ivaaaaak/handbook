package com.example.handbook.infra.kafka;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import com.example.handbook.domain.InputBoundary;
import com.example.handbook.domain.dto.InputBoundaryRq;

@Log4j2
@Component
@AllArgsConstructor
public class WarmupConsumer {

    private final InputBoundary inputBoundary;

    @Async("warmupThreadPool")
    @KafkaListener(
            topics = {"${spring.kafka.consumer.warmup-a-topic}"},
            clientIdPrefix = "warm-up-A"
    )
    public void warmUpA(@Headers Map<String, byte[]> headers, @Payload String message) {
        InputBoundaryRq inputBoundaryRq = InputBoundaryRq.builder()
                .headers(headers)
                .message(message)
                .targetTopic(InputBoundaryRq.WARM_UP_A)
                .build();
        inputBoundary.create(inputBoundaryRq);
    }

    @Async("warmupThreadPool")
    @KafkaListener(topics = {
            "${spring.kafka.consumer.warmup-b-topic}"},
            clientIdPrefix = "warm-up-b"
    )
    public void warmUpB(@Headers Map<String, byte[]> headers, @Payload String message) {
        InputBoundaryRq inputBoundaryRq = InputBoundaryRq.builder()
                .headers(headers)
                .message(message)
                .targetTopic(InputBoundaryRq.WARM_UP_B)
                .build();
        inputBoundary.create(inputBoundaryRq);
    }

    @Async("notifyThreadPool")
    @KafkaListener(
            topics = {"${spring.kafka.consumer.notify-a-topic}"},
            clientIdPrefix = "notify-A"
    )
    public void notifyChangedA(@Headers Map<String, byte[]> headers, @Payload String message) {
        InputBoundaryRq inputBoundaryRq = InputBoundaryRq.builder()
                .headers(headers)
                .message(message)
                .targetTopic(InputBoundaryRq.NOTIFY_A)
                .build();
        inputBoundary.create(inputBoundaryRq);
    }

    @Async("notifyThreadPool")
    @KafkaListener(
            topics = {"${spring.kafka.consumer.notify-b-topic}"},
            clientIdPrefix = "notify-B"
    )
    public void notifyChangedB(@Headers Map<String, byte[]> headers, @Payload String message) {
        InputBoundaryRq inputBoundaryRq = InputBoundaryRq.builder()
                .headers(headers)
                .message(message)
                .targetTopic(InputBoundaryRq.NOTIFY_B)
                .build();
        inputBoundary.create(inputBoundaryRq);
    }
}
