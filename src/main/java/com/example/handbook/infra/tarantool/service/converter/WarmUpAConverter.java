package com.example.handbook.infra.tarantool.service.converter;

import java.time.Instant;
import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Service;

import com.example.handbook.infra.tarantool.entity.*;
import com.example.handbook.infra.tarantool.util.DateUtil;
import com.example.handbook.domain.model.AWarmupMessage;
import com.example.handbook.infra.tarantool.dto.WarmUpACache;

@Service
public class WarmUpAConverter {

    private <T> boolean isNotNull(T object) {
        return object != null;
    }

    public WarmUpACache prepareWarmUpRCache(AWarmupMessage AWarmupMessage) {
        WarmUpACache warmUpACache = new WarmUpACache();
        if(isNotNull(AWarmupMessage.getClientInfo1s())) {
            for(AWarmupMessage.ClientInfo1 clientInfo1 : AWarmupMessage.getClientInfo1s()) {
                createWarmUpCache(AWarmupMessage.getMainInfo(), clientInfo1, warmUpACache);
            }
        }
        return warmUpACache;
    }

    private void createWarmUpCache(AWarmupMessage.MainInfo mainInfo,
                                   AWarmupMessage.ClientInfo1 clientInfo1,
                                   WarmUpACache warmUpACache) {
        Instant insTs = Instant.now();
        warmUpACache.getClientInfo1s().add(buildClientInfo1(mainInfo, clientInfo1, insTs));
        createClientInfo2s(clientInfo1, warmUpACache, insTs);
        createClientInfo5s(clientInfo1, warmUpACache, insTs);
        createClientInfo3s(clientInfo1, warmUpACache, insTs);
        createClientInfo6(clientInfo1, warmUpACache, insTs);
    }
    private void createClientInfo2s(AWarmupMessage.ClientInfo1 clientInfo1,
                                     WarmUpACache warmUpACache,
                                     Instant insTs) {
        if(isNotNull(clientInfo1.getClientInfo2s())) {
            for(AWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2 : clientInfo1.getClientInfo2s()) {
                warmUpACache.getClientInfo2s().add(buildClientInfo2s(clientInfo1, clientInfo2, insTs));
                createClientInfo4s(clientInfo1, clientInfo2, warmUpACache, insTs);
            }
        }
    }

    private void createClientInfo4s(AWarmupMessage.ClientInfo1 clientInfo1,
                                     AWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2,
                                     WarmUpACache warmUpACache,
                                     Instant insTs) {
        if(isNotNull(clientInfo2.getClientInfo4s())) {
            for(AWarmupMessage.ClientInfo1.ClientInfo2.ClientInfo4 clientInfo4 : clientInfo2.getClientInfo4s()) {
                warmUpACache.getClientInfo5s().add(buildClientInfo4(clientInfo1.getUniqueValue1(), clientInfo2, clientInfo4, insTs));
            }
        }
    }

    private void createClientInfo5s(AWarmupMessage.ClientInfo1 clientInfo1, WarmUpACache warmUpACache, Instant insTs) {
        if(isNotNull(clientInfo1.getClientInfo5s())) {
            for(AWarmupMessage.ClientInfo1.ClientInfo5 clientInfo5 : clientInfo1.getClientInfo5s()) {
                warmUpACache.getClientInfo4s().add(buildClientInfo5(clientInfo1.getUniqueValue1(), clientInfo5, insTs));
            }
        }
    }

    private void createClientInfo3s(AWarmupMessage.ClientInfo1 clientInfo1, WarmUpACache warmUpACache, Instant insTs) {
        if(isNotNull(clientInfo1.getClientInfo3s())) {
            for(AWarmupMessage.ClientInfo1.ClientInfo3 clientInfo3 : clientInfo1.getClientInfo3s()) {
                warmUpACache.getClientInfo3s().add(buildClientInfo3(clientInfo1.getUniqueValue1(), clientInfo3, insTs));
            }
        }
    }

    private void createClientInfo6(AWarmupMessage.ClientInfo1 clientInfo1, WarmUpACache warmUpACache, Instant insTs) {
        if (isNotNull(clientInfo1.getClientInfo6())) {
            warmUpACache.setClientInfo6(buildClientInfo6(clientInfo1.getUniqueValue1(), clientInfo1.getClientInfo6(), insTs));
        }
    }

    private ClientInfo1 buildClientInfo1(AWarmupMessage.MainInfo mainInfo, AWarmupMessage.ClientInfo1 clientInfo1, Instant insTs) {
        return ClientInfo1.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .value1(mainInfo.getValue1())
                .value2(mainInfo.getValue2())
                .value3(mainInfo.getValue3())
                .uniqueValue1(clientInfo1.getUniqueValue1())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo1.getLastChangeTime()))
                .build();
    }

    private ClientInfo2 buildClientInfo2s(AWarmupMessage.ClientInfo1 clientInfo1, AWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2, Instant insTs) {
        return ClientInfo2.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue1(clientInfo1.getUniqueValue1())
                .uniqueValue2(clientInfo2.getUniqueValue2())
                .value1(clientInfo2.getValue1())
                .value2(clientInfo2.getValue2())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo2.getLastChangeTime()))
                .build();
    }

    private ClientInfo5 buildClientInfo4(String uniqueValue1,
                                          AWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2,
                                          AWarmupMessage.ClientInfo1.ClientInfo2.ClientInfo4 clientInfo4,
                                          Instant insTs) {
        return ClientInfo5.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue345(clientInfo4.getId())
                .uniqueValue1(uniqueValue1)
                .value1(clientInfo4.getValue1())
                .value2(clientInfo4.getValue2())
                .value3(clientInfo4.getValue3())
                .uniqueValue2(clientInfo2.getUniqueValue2())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo4.getLastChangeTime()))
                .build();
    }

    private ClientInfo4 buildClientInfo5(String uniqueValue1, AWarmupMessage.ClientInfo1.ClientInfo5 clientInfo5, Instant insTs) {
        return ClientInfo4.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue1(uniqueValue1)
                .value1(clientInfo5.getValue1())
                .uniqueValue345(clientInfo5.getId())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo5.getLastChangeTime()))
                .build();
    }

    private ClientInfo3 buildClientInfo3(String uniqueValue1, AWarmupMessage.ClientInfo1.ClientInfo3 clientInfo3, Instant insTs) {
        return ClientInfo3.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue1(uniqueValue1)
                .uniqueValue345(clientInfo3.getId())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo3.getLastChangeTime()))
                .build();
    }

    private ClientInfo6 buildClientInfo6(String uniqueValue1, AWarmupMessage.ClientInfo1.ClientInfo6 clientInfo6, Instant insTs) {
        return ClientInfo6.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue1(uniqueValue1)
                .value1(clientInfo6.getValue1())
                .value2(clientInfo6.getValue2())
                .value3(clientInfo6.getValue3())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo6.getLastChangeTime()))
                .build();
    }
}