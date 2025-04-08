package com.example.handbook.domain;

import com.example.handbook.domain.dto.DatabaseGwRq;
import com.example.handbook.domain.dto.DatabaseGwRs;

public interface DatabaseGw {

    DatabaseGwRs launchControl(String channelType);
    DatabaseGwRs checkWarmupCompleted(String channelType);
    void updateWarmupCompleted(String channelType, int totalCount);

    void saveBufferNotify(DatabaseGwRq databaseGwRq);
    DatabaseGwRs getBufferNotifyBatch();
    void truncateBufferNotify();
}
