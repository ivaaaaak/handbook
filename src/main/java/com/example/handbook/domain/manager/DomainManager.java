package com.example.handbook.domain.manager;

import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.handbook.config.ApplicationProperties;
import com.example.handbook.domain.DatabaseGw;
import com.example.handbook.domain.dto.DatabaseGwRq;
import com.example.handbook.domain.model.BWarmupMessage;
import com.example.handbook.domain.InMemoryPlatformGw;
import com.example.handbook.domain.model.AWarmupMessage;
import com.example.handbook.domain.model.BNotifyMessage;
import com.example.handbook.infra.database.entity.BufferNotify;
import com.example.handbook.domain.model.ANotifyMessage;

import static com.example.handbook.domain.common.Constants.*;
import static com.example.handbook.infra.tarantool.util.Pause.operationalPause;

@Log4j2
@Component
@RequiredArgsConstructor
public class DomainManager {

    private final ObjectMapper objectMapper;
    private final DatabaseGw databaseGw;
    private final InMemoryPlatformGw inMemoryPlatformGw;
    private final ApplicationProperties applicationProperties;
    private boolean transferredNotifies = false;

    @Async("threadPoolForWarmupCompletedCheck")
    @Scheduled(
            fixedDelayString = "${application.check-warmup-fixed-delay}",
            initialDelayString = "${application.check-warmup-initial-delay}"
    )
    public void starNotificationProcessing() {
        if (!transferredNotifies && checkWarmupCompletedForAll()) {
            log.info("Warmup completed. Processing notifications from buffer");
            transferNotificationsBatches();
            databaseGw.truncateBufferNotify();
            transferredNotifies = true;
        }
    }

    protected void transferNotificationsBatches() {
        List<BufferNotify> notifications;
        do {
            notifications = databaseGw.getBufferNotifyBatch().getPackNotifies();
            parseAndNotifyDistributor(notifications);
            operationalPause(applicationProperties.getTtSavePause());

        } while (!notifications.isEmpty());
    }

    @Async("processNotifyThreadPool")
    @SneakyThrows
    protected void parseAndNotifyDistributor(List<BufferNotify> notifications) {
        for (BufferNotify notify : notifications) {
            String json = SerializationUtils.deserialize(notify.getMessage());
            Object cacheChangedNotify = objectMapper.readValue(
                    json,
                    TYPE_TO_OBJECT_MAP.get(notify.getType())
            );
            inMemoryPlatformGw.notifyDistributor(cacheChangedNotify);
            log.info("[] Run status of transfer from buffer");
        }
    }

    public boolean checkWarmupCompletedForAll() {
        return databaseGw.checkWarmupCompleted(A).isWarmupCompleted() &&
                databaseGw.checkWarmupCompleted(B).isWarmupCompleted();
    }

    protected void updateWarmupCompletedStatus(String channelType, int totalCount) {
        databaseGw.updateWarmupCompleted(channelType, totalCount);
    }

    public void saveWarmUpA(AWarmupMessage aWarmupMessage) {
        inMemoryPlatformGw.saveWarmUpA(aWarmupMessage);

        Integer currentNumber = aWarmupMessage.getHeaders().getWarmUpCacheCurrentNumber();
        Integer totalNumber = aWarmupMessage.getHeaders().getWarmUpCacheTotalCount();

        if (currentNumber.equals(totalNumber)) {
            updateWarmupCompletedStatus(A, totalNumber);
        }
    }

    public void saveWarmUpB(BWarmupMessage bWarmupMessage) {
        inMemoryPlatformGw.saveWarmUpB(bWarmupMessage);

        Integer currentNumber = bWarmupMessage.getHeaders().getWarmUpCacheCurrentNumber();
        Integer totalNumber = bWarmupMessage.getHeaders().getWarmUpCacheTotalCount();

        if (currentNumber.equals(totalNumber)) {
            updateWarmupCompletedStatus(B, totalNumber);
        }
    }

    public void saveNotifyA(ANotifyMessage notifyA) throws JsonProcessingException {
        if (checkWarmupCompletedForAll()) {
            inMemoryPlatformGw.notifyDistributor(notifyA);
        } else {
            String json = objectMapper.writeValueAsString(notifyA);
            databaseGw.saveBufferNotify(
                    DatabaseGwRq.builder()
                    .message(SerializationUtils.serialize(json))
                    .messageType(A)
                    .build()
            );
        }
    }

    public void saveNotifyB(BNotifyMessage notifyB) throws JsonProcessingException {
        if (checkWarmupCompletedForAll()) {
            inMemoryPlatformGw.notifyDistributor(notifyB);
        } else {
            String json = objectMapper.writeValueAsString(notifyB);
            databaseGw.saveBufferNotify(
                    DatabaseGwRq.builder()
                    .message(SerializationUtils.serialize(json))
                    .messageType(B)
                    .build()
            );
        }
    }
}
