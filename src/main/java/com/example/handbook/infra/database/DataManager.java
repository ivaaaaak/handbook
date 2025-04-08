package com.example.handbook.infra.database;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.handbook.config.ApplicationProperties;
import com.example.handbook.domain.DatabaseGw;
import com.example.handbook.domain.dto.DatabaseGwRq;
import com.example.handbook.domain.dto.DatabaseGwRs;
import com.example.handbook.infra.database.entity.BufferNotify;
import com.example.handbook.infra.database.repository.WarmupRepository;
import com.example.handbook.infra.database.entity.WarmUp;
import com.example.handbook.infra.tarantool.service.converter.ConverterKeeper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Log4j2
@Component
@AllArgsConstructor
public class DataManager implements DatabaseGw {

    private final WarmupRepository warmupRepository;
    private final ApplicationProperties appProp;
    protected final ConverterKeeper converterKeeper;

    @Override
    @Transactional
    public DatabaseGwRs launchControl(String channelType) {
        List<WarmUp> warmUpList = warmupRepository.getWarmUp(channelType);

        if (!warmUpList.isEmpty()) {
            return DatabaseGwRs.builder()
                    .needWarmUpStatus(false)
                    .build();
        } else {
            warmupRepository.insertWarmUp(
                    WarmUp.builder()
                            .warmupStatus(true)
                            .channelType(channelType)
                            .totalCount(0)
                            .insTs(Timestamp.from(Instant.now()))
                            .build()
            );
            return DatabaseGwRs.builder()
                    .needWarmUpStatus(true)
                    .build();
        }
    }

    @Override
    public DatabaseGwRs checkWarmupCompleted(String channelType) {
        List<WarmUp> warmUpList = warmupRepository.getWarmUp(channelType);

        if (warmUpList.isEmpty()) {
            return DatabaseGwRs.builder()
                    .warmupCompleted(false)
                    .build();
        }
        return DatabaseGwRs.builder()
                .warmupCompleted(!warmUpList.getFirst().getWarmupStatus())
                .build();
    }

    @Override
    public void updateWarmupCompleted(String channelType, int totalCount) {
        warmupRepository.updateWarmupCompleted(channelType, totalCount);
    }

    @Override
    public void saveBufferNotify(DatabaseGwRq databaseGwRq) {
        BufferNotify bufferNotify = BufferNotify.builder()
                .message(databaseGwRq.getMessage())
                .type(databaseGwRq.getMessageType())
                .workStatus(false)
                .build();
        warmupRepository.insertBufferNotify(bufferNotify);
    }

    @Override
    public DatabaseGwRs getBufferNotifyBatch() {
        List<BufferNotify> bufferNotifies = warmupRepository.getBufferNotifyBatch(appProp.getNumOfEntriesToMainCache());
        for(BufferNotify bufferNotify : bufferNotifies) {
            warmupRepository.updateBufferNotify(bufferNotify.getId(), true);
        }
        return DatabaseGwRs.builder().packNotifies(bufferNotifies).build();
    }

    @Override
    @Transactional
    public void truncateBufferNotify() {
        warmupRepository.truncateBufferNotify();
    }
}
