package com.example.handbook.infra.tarantool.service.converter;

import java.time.Instant;

import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Service;

import com.example.handbook.domain.model.BWarmupMessage;
import com.example.handbook.infra.tarantool.entity.*;
import com.example.handbook.infra.tarantool.util.DateUtil;
import com.example.handbook.infra.tarantool.dto.WarmUpBCache;

@Service
public class WarmUpBConverter {

    private <T> boolean isNotNull(T object) {
        return object != null;
    }

    public WarmUpBCache prepareWarmUpBCache(BWarmupMessage BWarmupMessage) {
        WarmUpBCache warmUpBCache = new WarmUpBCache();
        if(isNotNull(BWarmupMessage.getClientInfo1s())) {
            for(BWarmupMessage.ClientInfo1 clientInfo1 : BWarmupMessage.getClientInfo1s()) {
                createWarmUpBCache(BWarmupMessage.getMainInfo(), clientInfo1, warmUpBCache);
            }
        }
        return warmUpBCache;
    }

    private void createWarmUpBCache(BWarmupMessage.MainInfo mainInfo,
                                        BWarmupMessage.ClientInfo1 clientInfo1,
                                        WarmUpBCache warmUpBCache) {
        Instant insTs = Instant.now();
        warmUpBCache.getClientInfo1s().add(buildClientInfo1(mainInfo, clientInfo1, insTs));
        createClientInfo2s(clientInfo1, warmUpBCache, insTs);
        createClientInfo5s(clientInfo1, warmUpBCache, insTs);
        createClientInfo3s(clientInfo1, warmUpBCache, insTs);
    }

    private void createClientInfo2s(BWarmupMessage.ClientInfo1 clientInfo1,
                                     WarmUpBCache warmUpBCache,
                                     Instant insTs) {
        if(isNotNull(clientInfo1.getClientInfo2s())) {
            for(BWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2 : clientInfo1.getClientInfo2s()) {
                warmUpBCache.getClientInfo2s().add(buildClientInfo2s(clientInfo1, clientInfo2, insTs));
                createClientInfo4s(clientInfo1, clientInfo2, warmUpBCache, insTs);
            }
        }
    }

    private void createClientInfo4s(BWarmupMessage.ClientInfo1 clientInfo1,
                                     BWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2,
                                     WarmUpBCache warmUpBCache,
                                     Instant insTs) {
        if(isNotNull(clientInfo2.getClientInfo4s())) {
            for(BWarmupMessage.ClientInfo1.ClientInfo2.ClientInfo4 clientInfo4 : clientInfo2.getClientInfo4s()) {
                warmUpBCache.getClientInfo5s().add(buildClientInfo4(clientInfo1, clientInfo4, insTs, clientInfo2));
            }
        }
    }

    private void createClientInfo5s(BWarmupMessage.ClientInfo1 clientInfo1, WarmUpBCache warmUpBCache, Instant insTs) {
        if(isNotNull(clientInfo1.getClientInfo5s())) {
            for(BWarmupMessage.ClientInfo1.ClientInfo5 clientInfo5 : clientInfo1.getClientInfo5s()) {
                warmUpBCache.getClientInfo4s().add(buildClientInfo5(clientInfo1, clientInfo5, insTs));
            }
        }
    }

    private void createClientInfo3s(BWarmupMessage.ClientInfo1 clientInfo1, WarmUpBCache warmUpBCache, Instant insTs) {
        if(isNotNull(clientInfo1.getClientInfo3s())) {
            for(BWarmupMessage.ClientInfo1.ClientInfo3 clientInfo3 : clientInfo1.getClientInfo3s()) {
                warmUpBCache.getClientInfo3s().add(buildClientInfo3(clientInfo1, clientInfo3, insTs));
            }
        }
    }

    private ClientInfo1 buildClientInfo1(
            BWarmupMessage.MainInfo mainInfo,
            BWarmupMessage.ClientInfo1 clientInfo1,
            Instant insTs
    ) {
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

    private ClientInfo2 buildClientInfo2s(
            BWarmupMessage.ClientInfo1 clientInfo1,
            BWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2,
            Instant insTs
    ) {
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

    private ClientInfo5 buildClientInfo4(
            BWarmupMessage.ClientInfo1 clientInfo1,
            BWarmupMessage.ClientInfo1.ClientInfo2.ClientInfo4 clientInfo4,
            Instant insTs,
            BWarmupMessage.ClientInfo1.ClientInfo2 clientInfo2
    ) {
        return ClientInfo5.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue345(clientInfo4.getId().intValue())
                .uniqueValue1(clientInfo1.getUniqueValue1())
                .value1(clientInfo4.getValue1())
                .value2(clientInfo4.getValue2())
                .value3(clientInfo4.getValue3().intValue())
                .uniqueValue2(clientInfo2.getUniqueValue2())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo4.getLastChangeTime()))
                .build();
    }

    private ClientInfo4 buildClientInfo5(
            BWarmupMessage.ClientInfo1 clientInfo1,
            BWarmupMessage.ClientInfo1.ClientInfo5 clientInfo5,
            Instant insTs
    ) {
        return ClientInfo4.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue1(clientInfo1.getUniqueValue1())
                .value1(clientInfo5.getValue1())
                .uniqueValue345(clientInfo5.getId().intValue())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo5.getLastChangeTime()))
                .build();
    }

    private ClientInfo3 buildClientInfo3(
            BWarmupMessage.ClientInfo1 clientInfo1,
            BWarmupMessage.ClientInfo1.ClientInfo3 clientInfo3,
            Instant insTs
    ) {
        return ClientInfo3.builder()
                .id(Generators.timeBasedEpochGenerator().generate())
                .uniqueValue1(clientInfo1.getUniqueValue1())
                .uniqueValue345(clientInfo3.getId().intValue())
                .insTs(insTs)
                .updTs(DateUtil.toInstant(clientInfo3.getLastChangeTime()))
                .build();
    }
}
