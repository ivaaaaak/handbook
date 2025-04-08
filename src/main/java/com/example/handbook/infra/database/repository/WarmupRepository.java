package com.example.handbook.infra.database.repository;

import com.example.handbook.infra.database.entity.BufferNotify;
import com.example.handbook.infra.database.entity.WarmUp;

import java.util.List;

public interface WarmupRepository {

    void insertWarmUp(WarmUp warmUp);
    List<WarmUp> getWarmUp(String channelType);
    void updateWarmupCompleted(String channelType, int totalCount);

    void insertBufferNotify(BufferNotify bufferNotify);
    List<BufferNotify> getBufferNotifyBatch(int batchSize);
    void updateBufferNotify(Long id, Boolean workStatus);
    void truncateBufferNotify();
}
