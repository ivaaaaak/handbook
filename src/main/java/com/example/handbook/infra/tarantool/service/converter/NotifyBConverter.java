package com.example.handbook.infra.tarantool.service.converter;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Service;

import com.example.handbook.domain.model.BNotifyMessage;
import com.example.handbook.infra.tarantool.dto.NotifyBCache;
import com.example.handbook.infra.tarantool.entity.*;
import com.example.handbook.infra.tarantool.util.DateUtil;
import com.example.handbook.infra.tarantool.dto.Attribute;

import static com.example.handbook.infra.tarantool.common.TTConstant.SUPPORTED_TABLES;

@Service
public class NotifyBConverter {

    public NotifyBCache prepareChangedNotifyBCache(BNotifyMessage notifyB) {
        NotifyBCache notifyBCache = new NotifyBCache();
        notifyBCache.setUniqueValue1(notifyB.getNotify().getUniqueValue1());
        BNotifyMessage.Profile profile = notifyB.getProfile();
        Instant insTs = Instant.now();
        if (profile != null) {
            BNotifyMessage.Profile.MainInfo mainInfo = profile.getMainInfo();
            List<BNotifyMessage.Profile.ClientInfo1> ClientInfo1sInner = profile.getClientInfo1s();
            notifyBCache.setValue2(mainInfo.getValue2());
            List<ClientInfo1> clientInfo1s = new ArrayList<>();
            if (ClientInfo1sInner != null) {
                if (ClientInfo1sInner.isEmpty()) {
                    clientInfo1s.add(ClientInfo1.builder()
                            .id(Generators.timeBasedEpochGenerator().generate())
                            .value1(mainInfo.getValue1())
                            .value2(mainInfo.getValue2())
                            .value3(mainInfo.getValue3())
                            .build());
                }
                for (BNotifyMessage.Profile.ClientInfo1 clientInfo1 : ClientInfo1sInner) {
                    clientInfo1s.add(ClientInfo1.builder()
                            .id(Generators.timeBasedEpochGenerator().generate())
                            .value2(mainInfo.getValue2())
                            .value1(mainInfo.getValue1())
                            .value3(mainInfo.getValue3())
                            .uniqueValue1(clientInfo1.getUniqueValue1())
                            .insTs(insTs)
                            .updTs(DateUtil.toInstant(clientInfo1.getLastChangeTime()))
                            .build());
                    prepareClientInfo2s(clientInfo1, notifyBCache, insTs);
                    prepareClientInfo5(clientInfo1, notifyBCache, insTs);
                    prepareClientInfo3(clientInfo1, notifyBCache, insTs);
                }
            }
            notifyBCache.getClientInfo1s().addAll(clientInfo1s);
            notifyBCache.getChangeAttributes().addAll(
                    prepareAttributes(notifyB)
            );
        } else {
            notifyBCache.setValue2(notifyB.getNotify().getValue2());
        }
        return notifyBCache;
    }

    private void prepareClientInfo2s(BNotifyMessage.Profile.ClientInfo1 clientInfo1,
                                     NotifyBCache NotifyBCache,
                                     Instant insTs) {
        List<ClientInfo2> clientInfo2s = new ArrayList<>();
        if (clientInfo1.getClientInfo2s() != null) {
            for (BNotifyMessage.Profile.ClientInfo1.ClientInfo2 clientInfo2 : clientInfo1.getClientInfo2s()) {
                clientInfo2s.add(ClientInfo2.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .uniqueValue2(clientInfo2.getUniqueValue2())
                        .value1(clientInfo2.getValue1())
                        .value2(clientInfo2.getValue2())
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(clientInfo2.getLastChangeTime()))
                        .build());
                prepareClientInfo4(clientInfo1, clientInfo2, NotifyBCache, insTs);
            }
        }
        NotifyBCache.getClientInfo2s().addAll(clientInfo2s);
    }

    private void prepareClientInfo4(BNotifyMessage.Profile.ClientInfo1 clientInfo1,
                                    BNotifyMessage.Profile.ClientInfo1.ClientInfo2 clientInfo2Inner,
                                    NotifyBCache NotifyBCache,
                                    Instant insTs) {
        List<ClientInfo5> clientInfo5s = new ArrayList<>();
        if (clientInfo2Inner.getClientInfo4s() != null) {
            for (BNotifyMessage.Profile.ClientInfo1.ClientInfo2.ClientInfo4 clientInfo4Inner : clientInfo2Inner.getClientInfo4s()) {
                clientInfo5s.add(ClientInfo5.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue345(Math.toIntExact(clientInfo4Inner.getId()))
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .value1(clientInfo4Inner.getValue1())
                        .value2(clientInfo4Inner.getValue2())
                        .value3(Math.toIntExact(clientInfo4Inner.getValue3()))
                        .uniqueValue2(clientInfo2Inner.getUniqueValue2())
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(clientInfo4Inner.getLastChangeTime()))
                        .build());
            }
        }
        NotifyBCache.getClientInfo5s().addAll(clientInfo5s);
    }

    private void prepareClientInfo5(BNotifyMessage.Profile.ClientInfo1 clientInfo1, NotifyBCache NotifyBCache, Instant insTs) {
        List<ClientInfo4> clientInfo4s = new ArrayList<>();
        if (clientInfo1.getClientInfo5s() != null) {
            for (BNotifyMessage.Profile.ClientInfo1.ClientInfo5 ClientInfo5Inner : clientInfo1.getClientInfo5s()) {
                clientInfo4s.add(ClientInfo4.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .value1(clientInfo1.getUniqueValue1())
                        .uniqueValue345(Math.toIntExact(ClientInfo5Inner.getId()))
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(ClientInfo5Inner.getLastChangeTime()))
                        .build());
            }
        }
        NotifyBCache.getClientInfo4s().addAll(clientInfo4s);
    }

    private void prepareClientInfo3(BNotifyMessage.Profile.ClientInfo1 clientInfo1, NotifyBCache NotifyBCache, Instant insTs) {
        List<ClientInfo3> clientInfo3s = new ArrayList<>();
        if (clientInfo1.getClientInfo3s() != null) {
            for (BNotifyMessage.Profile.ClientInfo1.ClientInfo3 clientInfo3 : clientInfo1.getClientInfo3s()) {
                clientInfo3s.add(ClientInfo3.builder()
                        .id(Generators.timeBasedEpochGenerator().generate())
                        .uniqueValue1(clientInfo1.getUniqueValue1())
                        .uniqueValue345(Math.toIntExact(clientInfo3.getId()))
                        .insTs(insTs)
                        .updTs(DateUtil.toInstant(clientInfo3.getLastChangeTime()))
                        .build());
            }
        }
        NotifyBCache.getClientInfo3s().addAll(clientInfo3s);
    }

    private List<Attribute> prepareAttributes(BNotifyMessage cacheChangedNotify) {
        List<Attribute> changeAttributes = new ArrayList<>();
        for (BNotifyMessage.Notify.Attribute attribute : cacheChangedNotify.getNotify().getAttributes()) {
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

    private List<Attribute.Field> prepareField(BNotifyMessage.Notify.Attribute attribute) {
        List<Attribute.Field> changeFields = new ArrayList<>();
        if (attribute.getFields() != null) {
            for (BNotifyMessage.Notify.Attribute.Field field : attribute.getFields()) {
                changeFields.add(Attribute.Field.builder()
                        .fieldName(field.getFieldName())
                        .oldValue(field.getOldValue())
                        .newValue(field.getNewValue())
                        .build());
            }
        }
        return changeFields;
    }
}
