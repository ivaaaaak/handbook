package com.example.handbook.infra.tarantool.service.converter;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Service;

import com.example.handbook.infra.tarantool.entity.*;
import com.example.handbook.infra.tarantool.dto.Attribute;
import com.example.handbook.infra.tarantool.util.DateUtil;
import com.example.handbook.domain.model.ANotifyMessage;
import com.example.handbook.infra.tarantool.dto.NotifyACache;

import static com.example.handbook.infra.tarantool.common.TTConstant.SUPPORTED_TABLES;

@Service
public class NotifyAConverter {

    public NotifyACache prepareChangedNotifyCache(ANotifyMessage notifyA) {

        NotifyACache notifyACache = new NotifyACache();
        ANotifyMessage.Profile profile = notifyA.getProfile();
        Instant insTs = Instant.now();
        
        if (profile != null) {
            ANotifyMessage.Profile.MainInfo mainInfo = profile.getMainInfo();
            List<ANotifyMessage.Profile.ClientInfo1> clientInfo1sInner = profile.getClientInfo1s();
            notifyACache.setValue2(mainInfo.getValue2());
            List<ClientInfo1> clientInfo1s = new ArrayList<>();
            if (clientInfo1sInner != null) {
                ClientInfo1 clientInfo1;
                for (ANotifyMessage.Profile.ClientInfo1 clientInfo1Inner : clientInfo1sInner) {
                    clientInfo1 = ClientInfo1.builder()
                            .id(Generators.timeBasedEpochGenerator().generate())
                            .value1(mainInfo.getValue1())
                            .value2(mainInfo.getValue2())
                            .value3(mainInfo.getValue3())
                            .uniqueValue1(clientInfo1Inner.getUniqueValue1())
                            .insTs(insTs)
                            .updTs(DateUtil.toInstant(clientInfo1Inner.getLastChangeTime()))
                            .build();
                    clientInfo1s.add(clientInfo1);
                    prepareClientInfo2s(clientInfo1Inner, notifyACache, insTs);
                    prepareClientInfo5(clientInfo1Inner, notifyACache, insTs);
                    prepareClientInfo3(clientInfo1Inner, notifyACache, insTs);
                    prepareClientInfo6(clientInfo1Inner, notifyACache, insTs);
                }
            }
            notifyACache.getClientInfo1s().addAll(clientInfo1s);
            notifyACache.getChangeAttributes().addAll(prepareAttributes(notifyA));
        } else {
            notifyACache.setValue2(notifyA.getNotify().getValue2());
        }
        return notifyACache;
    }

    private List<Attribute> prepareAttributes(ANotifyMessage cacheChangedNotify) {
        List<Attribute> changeAttributes = new ArrayList<>();
        for (ANotifyMessage.Notify.Attribute attribute : cacheChangedNotify.getNotify().getAttributes()) {
            if (SUPPORTED_TABLES.contains(attribute.getAttribute().toUpperCase())) {
                changeAttributes.add(Attribute.builder()
                        .attribute(attribute.getAttribute())
                        .changeType(attribute.getChangeType())
                        .key(attribute.getKey())
                        .fields(prepareField(attribute))
                        .build());
            }
        }
        return changeAttributes;
    }

    private List<Attribute.Field> prepareField(ANotifyMessage.Notify.Attribute attribute) {
        List<Attribute.Field> changeFields = new ArrayList<>();
        if (attribute.getFields() != null) {
            for (ANotifyMessage.Notify.Attribute.Field field : attribute.getFields()) {
                changeFields.add(Attribute.Field.builder()
                        .fieldName(field.getFieldName())
                        .oldValue(field.getOldValue())
                        .newValue(field.getNewValue())
                        .build());
            }
        }
        return changeFields;
    }

    private void prepareClientInfo2s(ANotifyMessage.Profile.ClientInfo1 clientInfo1,
                                      NotifyACache notifyACache,
                                      Instant insTs) {
        List<ClientInfo2> clientInfo2s = new ArrayList<>();
        if (clientInfo1.getClientInfo2s() != null) {
            for (ANotifyMessage.Profile.ClientInfo1.ClientInfo2 clientInfo2 : clientInfo1.getClientInfo2s()) {
                clientInfo2s.add(ClientInfo2.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .uniqueValue2(clientInfo2.getUniqueValue2())
                        .value1(clientInfo2.getValue1())
                        .value2(clientInfo2.getValue2())
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(clientInfo2.getLastChangeTime()))
                        .build());
                prepareClientInfo4(clientInfo1, clientInfo2, notifyACache, insTs);
            }
        }
        notifyACache.getClientInfo2s().addAll(clientInfo2s);
    }

    private void prepareClientInfo4(ANotifyMessage.Profile.ClientInfo1 clientInfo1,
                                     ANotifyMessage.Profile.ClientInfo1.ClientInfo2 clientInfo2Inner,
                                     NotifyACache notifyACache,
                                     Instant insTs) {
        List<ClientInfo5> clientInfo5s = new ArrayList<>();
        if (clientInfo2Inner.getClientInfo4s() != null) {
            for (ANotifyMessage.Profile.ClientInfo1.ClientInfo2.ClientInfo4 clientInfo4Inner : clientInfo2Inner.getClientInfo4s()) {
                clientInfo5s.add(ClientInfo5.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue345(clientInfo4Inner.getId())
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .value1(clientInfo4Inner.getValue1())
                        .value2(clientInfo4Inner.getValue2())
                        .value3(clientInfo4Inner.getValue3())
                        .uniqueValue2(clientInfo2Inner.getUniqueValue2())
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(clientInfo4Inner.getLastChangeTime()))
                        .build());
            }
        }
        notifyACache.getClientInfo5s().addAll(clientInfo5s);
    }

    private void prepareClientInfo5(ANotifyMessage.Profile.ClientInfo1 clientInfo1, NotifyACache notifyACache, Instant insTs) {
        List<ClientInfo4> clientInfo4s = new ArrayList<>();
        if (clientInfo1.getClientInfo5s() != null) {
            for (ANotifyMessage.Profile.ClientInfo1.ClientInfo5 ClientInfo5Inner : clientInfo1.getClientInfo5s()) {
                clientInfo4s.add(ClientInfo4.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .value1(clientInfo1.getUniqueValue1())
                        .uniqueValue345(ClientInfo5Inner.getId())
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(ClientInfo5Inner.getLastChangeTime()))
                        .build());
            }
        }
        notifyACache.getClientInfo4s().addAll(clientInfo4s);
    }

    private void prepareClientInfo3(ANotifyMessage.Profile.ClientInfo1 clientInfo1, NotifyACache notifyACache, Instant insTs) {
        List<ClientInfo3> clientInfo3s = new ArrayList<>();
        if (clientInfo1.getClientInfo3s() != null) {
            for (ANotifyMessage.Profile.ClientInfo1.ClientInfo3 clientInfo3 : clientInfo1.getClientInfo3s()) {
                clientInfo3s.add(ClientInfo3.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .uniqueValue345(clientInfo3.getId())
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(clientInfo3.getLastChangeTime()))
                        .build());
            }
        }
        notifyACache.getClientInfo3s().addAll(clientInfo3s);
    }

    private void prepareClientInfo6(ANotifyMessage.Profile.ClientInfo1 clientInfo1, NotifyACache notifyACache, Instant insTs) {
        ANotifyMessage.Profile.ClientInfo1.ClientInfo6 clientInfo6 = clientInfo1.getClientInfo6();
        if (clientInfo6 != null) {
            notifyACache.setClientInfo6(ClientInfo6.builder()
                    .id(Generators.timeBasedEpochGenerator().generate())
                    .uniqueValue1(clientInfo1.getUniqueValue1())
                    .value1(clientInfo6.getValue1())
                    .value2(clientInfo6.getValue2())
                    .value3(clientInfo6.getValue3())
                    .insTs(insTs)
                    .updTs(DateUtil.toInstant(clientInfo6.getLastChangeTime()))
                    .build());
        }
    }
}
