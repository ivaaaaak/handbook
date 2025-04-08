package com.example.handbook.domain;

import com.example.handbook.domain.model.BWarmupMessage;
import com.example.handbook.domain.model.AWarmupMessage;

public interface InMemoryPlatformGw {

    boolean checkTarantoolConnection();
    void saveWarmUpA(AWarmupMessage AWarmupMessage);
    void saveWarmUpB(BWarmupMessage BWarmupMessage);
    <T> void notifyDistributor(T cacheNotify);
}
