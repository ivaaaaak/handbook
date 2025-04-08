package com.example.handbook.infra.tarantool.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.example.handbook.infra.tarantool.dto.*;
import com.example.handbook.infra.tarantool.entity.*;
import com.example.handbook.infra.tarantool.entity.ClientInfo1;
import com.example.handbook.infra.tarantool.service.crud.CrudKeeper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.handbook.infra.tarantool.common.TTConstant.*;

@Log4j2
@Service
@AllArgsConstructor
public class ReferenceDataService {
    
    private final CrudKeeper crudKeeper;
    
    public void createAProfile(NotifyACache notifyACache) {
        notifyACache.getClientInfo1s().forEach(crudKeeper.getCreateTT()::saveClientInfo1);
        notifyACache.getClientInfo2s().forEach(crudKeeper.getCreateTT()::saveClientInfo2);
        notifyACache.getClientInfo5s().forEach(crudKeeper.getCreateTT()::saveClientInfo5);
        notifyACache.getClientInfo4s().forEach(crudKeeper.getCreateTT()::saveClientInfo4);
        notifyACache.getClientInfo3s().forEach(crudKeeper.getCreateTT()::saveClientInfo3);
        if (notifyACache.getClientInfo6() != null) {
            crudKeeper.getCreateTT().saveClientInfo6(notifyACache.getClientInfo6());
        }
    }

    public void createBProfile(NotifyBCache notifyBCache) {
        notifyBCache.getClientInfo1s().forEach(crudKeeper.getCreateTT()::saveClientInfo1);
        notifyBCache.getClientInfo2s().forEach(crudKeeper.getCreateTT()::saveClientInfo2);
        notifyBCache.getClientInfo5s().forEach(crudKeeper.getCreateTT()::saveClientInfo5);
        notifyBCache.getClientInfo4s().forEach(crudKeeper.getCreateTT()::saveClientInfo4);
        notifyBCache.getClientInfo3s().forEach(crudKeeper.getCreateTT()::saveClientInfo3);
    }

    @Async("saveAThreadPool")
    public void saveAClientInfo1(WarmUpACache warmUpACache) {
        for(ClientInfo1 clientInfo1 : warmUpACache.getClientInfo1s()) {
            crudKeeper.getCreateTT().saveClientInfo1(clientInfo1);
        }
    }

    @Async("saveAThreadPool")
    public void saveAClientInfo2(WarmUpACache warmUpACache) {
        for(ClientInfo2 clientInfo2 : warmUpACache.getClientInfo2s()) {
            crudKeeper.getCreateTT().saveClientInfo2(clientInfo2);
        }
    }

    @Async("saveAThreadPool")
    public void saveAClientInfo5(WarmUpACache warmUpACache) {
        for(ClientInfo5 clientInfo5 : warmUpACache.getClientInfo5s()) {
            crudKeeper.getCreateTT().saveClientInfo5(clientInfo5);
        }
    }

    @Async("saveAThreadPool")
    public void saveAClientInfo4(WarmUpACache warmUpACache) {
        for(ClientInfo4 clientInfo4 : warmUpACache.getClientInfo4s()) {
            crudKeeper.getCreateTT().saveClientInfo4(clientInfo4);
        }
    }

    @Async("saveAThreadPool")
    public void saveAClientInfo6(WarmUpACache warmUpACache) {
        if(warmUpACache.getClientInfo6() != null) {
            crudKeeper.getCreateTT().saveClientInfo6(warmUpACache.getClientInfo6());
        }
    }

    @Async("saveAThreadPool")
    public void saveAClientInfo3(WarmUpACache warmUpACache) {
        for(ClientInfo3 clientInfo3 : warmUpACache.getClientInfo3s()) {
            crudKeeper.getCreateTT().saveClientInfo3(clientInfo3);
        }
    }

    @Async("saveBThreadPool")
    public void saveBClientInfo1(WarmUpBCache warmUpBCache) {
        for(ClientInfo1 clientInfo1 : warmUpBCache.getClientInfo1s()) {
            crudKeeper.getCreateTT().saveClientInfo1(clientInfo1);
        }
    }

    @Async("saveBThreadPool")
    public void saveBClientInfo2(WarmUpBCache warmUpBCache) {
        for(ClientInfo2 clientInfo2 : warmUpBCache.getClientInfo2s()) {
            crudKeeper.getCreateTT().saveClientInfo2(clientInfo2);
        }
    }

    @Async("saveBThreadPool")
    public void saveBClientInfo5(WarmUpBCache warmUpBCache) {
        for(ClientInfo5 clientInfo5 : warmUpBCache.getClientInfo5s()) {
            crudKeeper.getCreateTT().saveClientInfo5(clientInfo5);
        }
    }

    @Async("saveBThreadPool")
    public void saveBClientInfo4(WarmUpBCache warmUpBCache) {
        for(ClientInfo4 clientInfo4 : warmUpBCache.getClientInfo4s()) {
            crudKeeper.getCreateTT().saveClientInfo4(clientInfo4);
        }
    }

    @Async("saveBThreadPool")
    public void saveBClientInfo3(WarmUpBCache warmUpBCache) {
        for(ClientInfo3 clientInfo3 : warmUpBCache.getClientInfo3s()) {
            crudKeeper.getCreateTT().saveClientInfo3(clientInfo3);
        }
    }

    public void updateAProfileIfNeeded(NotifyACache notifyACache) {
        List<Attribute> changedAttributes = notifyACache.getChangeAttributes();
        if (hasAddModificationOperation(changedAttributes)) {
            updateAProfile(notifyACache);
        } else {
            Map<String, List<Attribute>> supportedAttributes = getSupportedAttributesForUpdate(changedAttributes);
            if (!CollectionUtils.isEmpty(supportedAttributes.keySet())) {
                updateAProfile(notifyACache);
            }
        }
    }

    public void updateBProfileIfNeeded(NotifyBCache notifyBCache) {
        List<Attribute> changedAttributes = notifyBCache.getChangeAttributes();
        if (hasAddModificationOperation(changedAttributes)) {
            updateBProfile(notifyBCache);
        } else {
            Map<String, List<Attribute>> supportedAttributes = getSupportedAttributesForUpdate(changedAttributes);
            if (!CollectionUtils.isEmpty(supportedAttributes.keySet())) {
                updateBProfile(notifyBCache);
            }
        }
    }

    public void deleteAllAProfiles(NotifyACache notifyACache) {
        List<ClientInfo1> clientInfo1s = crudKeeper.getDeleteTT().deleteClientInfo1sByvalue2(notifyACache.getValue2());
        crudKeeper.getDeleteTT().deleteAllEntriesByUniqueValue1(clientInfo1s);
    }

    public void deleteAllBProfiles(NotifyBCache notifyBCache) {
        List<ClientInfo1> clientInfo1s = crudKeeper.getDeleteTT().deleteClientInfo1sByvalue2(notifyBCache.getValue2());
        crudKeeper.getDeleteTT().deleteAllEntriesByUniqueValue1(clientInfo1s);
    }

    public void deleteBProfiles(NotifyBCache notifyBCache) {
        List<ClientInfo1> BDelClientInfo1s = crudKeeper.getDeleteTT().deleteBClientInfo1sBy(notifyBCache.getValue2(), null);

        if (BDelClientInfo1s.isEmpty()) {
            BDelClientInfo1s = crudKeeper.getDeleteTT().deleteBClientInfo1sBy(
                    notifyBCache.getValue2(),
                    notifyBCache.getUniqueValue1()
            );

            if (!BDelClientInfo1s.isEmpty()) {
                crudKeeper.getDeleteTT().deleteAllEntriesByUniqueValue1(BDelClientInfo1s);
            }
        }
    }

    private boolean hasAddModificationOperation(List<Attribute> changeAttributes) {
        return changeAttributes.stream().anyMatch(attribute -> MODIFY_ADD.equalsIgnoreCase(attribute.getChangeType()));
    }

    private void updateAProfile(NotifyACache notifyACache) {
        deleteAllAProfiles(notifyACache);
        createAProfile(notifyACache);
    }

    private void updateBProfile(NotifyBCache notifyBCache) {
        deleteBProfiles(notifyBCache);
        createBProfile(notifyBCache);
    }

    private Map<String, List<Attribute>> getSupportedAttributesForUpdate(List<Attribute> changeAttributes) {
        return changeAttributes.stream()
                .filter(attribute -> !MODIFY_ADD.equalsIgnoreCase(attribute.getChangeType()))
                .filter(
                        attribute -> MODIFY_DELETE.equalsIgnoreCase(attribute.getChangeType())
                                || hasSupportedFields(attribute)
                )
                .collect(Collectors.groupingBy(attribute -> attribute.getAttribute().toUpperCase()));
    }

    private boolean hasSupportedFields(Attribute attribute) {
        List<String> classFieldsNames = getClassFieldsNames(attribute.getAttribute());
        return Optional.ofNullable(attribute.getFields())
                .stream()
                .flatMap(List::stream)
                .anyMatch(field -> classFieldsNames.contains(field.getFieldName().toLowerCase()));
    }

    private List<String> getClassFieldsNames(String className) {
        List<String> fields;
        switch (className) {
            case MAIN_INFO:
            case CLIENT_INFO1:
                fields = getClassFieldsNames(ClientInfo1.class);
                break;
            case CLIENT_INFO2:
                fields = getClassFieldsNames(ClientInfo2.class);
                break;
            case CLIENT_INFO5:
                fields = getClassFieldsNames(ClientInfo5.class);
                break;
            case CLIENT_INFO4:
                fields = getClassFieldsNames(ClientInfo4.class);
                break;
            case CLIENT_INFO3:
                fields = getClassFieldsNames(ClientInfo3.class);
                break;
            case CLIENT_INFO6:
                fields = getClassFieldsNames(ClientInfo6.class);
                break;
            default:
                return Collections.emptyList();
        }
        return fields;
    }

    private <T> List<String> getClassFieldsNames(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
