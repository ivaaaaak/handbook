package com.example.handbook.infra.tarantool;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import com.example.handbook.domain.model.ANotifyMessage;
import com.example.handbook.domain.model.BNotifyMessage;
import com.example.handbook.domain.model.BWarmupMessage;
import com.example.handbook.domain.InMemoryPlatformGw;
import com.example.handbook.domain.model.AWarmupMessage;
import com.example.handbook.infra.tarantool.dto.NotifyACache;
import com.example.handbook.infra.tarantool.dto.NotifyBCache;
import com.example.handbook.infra.tarantool.service.crud.*;
import com.example.handbook.infra.tarantool.dto.WarmUpACache;
import com.example.handbook.infra.tarantool.service.converter.*;
import com.example.handbook.infra.tarantool.dto.WarmUpBCache;
import com.example.handbook.infra.tarantool.service.ReferenceDataService;

import static com.example.handbook.infra.tarantool.common.TTConstant.*;
import static com.example.handbook.infra.tarantool.common.TTConstant.PROFILE_OPERATION_DELETE;

@Log4j2
@Component
@RequiredArgsConstructor
public class TTManager implements InMemoryPlatformGw {

    private final CrudKeeper crudKeeper;
    private final ConverterKeeper converterKeeper;
    private final ReferenceDataService referenceDataService;

    @Override
    public boolean checkTarantoolConnection() {
        return crudKeeper.getReadTT().checkTarantool();
    }

    public <T> void notifyDistributor(T cacheNotify) {
        if (cacheNotify instanceof ANotifyMessage notifyA) {
            processingANotification(notifyA);

        } else if (cacheNotify instanceof BNotifyMessage notifyB) {
            processingBNotification(notifyB);
        }
    }

    private void processingANotification(ANotifyMessage notifyA) {
        NotifyACache notifyACache = converterKeeper.getNotifyAConverter().prepareChangedNotifyCache(notifyA);
        String profileOperation = notifyA.getNotify().getProfileOperation();

        if (PROFILE_OPERATION_NEW.equalsIgnoreCase(profileOperation)) {
            referenceDataService.createAProfile(notifyACache);

        } else if (PROFILE_OPERATION_MODIFY.equalsIgnoreCase(profileOperation)) {
            referenceDataService.updateAProfileIfNeeded(notifyACache);

        } else if (PROFILE_OPERATION_DELETE.equalsIgnoreCase(profileOperation)) {
            referenceDataService.deleteAllAProfiles(notifyACache);
        }
    }

    private void processingBNotification(BNotifyMessage notifyB) {
        NotifyBCache notifyBCache = converterKeeper.getNotifyBConverter().prepareChangedNotifyBCache(notifyB);
        String profileOperation = notifyB.getNotify().getProfileOperation();

        if (PROFILE_OPERATION_NEW.equalsIgnoreCase(profileOperation)) {
            referenceDataService.createBProfile(notifyBCache);

        } else if (PROFILE_OPERATION_MODIFY.equalsIgnoreCase(profileOperation)) {
            referenceDataService.updateBProfileIfNeeded(notifyBCache);

        } else if (PROFILE_OPERATION_DELETE.equalsIgnoreCase(profileOperation)) {
            referenceDataService.deleteAllBProfiles(notifyBCache);
        }
    }

    @Override
    public void saveWarmUpA(AWarmupMessage AWarmupMessage) {
        WarmUpACache warmUpACache = converterKeeper.getWarmUpAConverter().prepareWarmUpRCache(AWarmupMessage);
        Integer totalCount = AWarmupMessage.getHeaders().getWarmUpCacheTotalCount();
        Integer currentNumber = AWarmupMessage.getHeaders().getWarmUpCacheCurrentNumber();
        
        referenceDataService.saveAClientInfo1(warmUpACache);
        referenceDataService.saveAClientInfo2(warmUpACache);
        referenceDataService.saveAClientInfo4(warmUpACache);
        referenceDataService.saveAClientInfo5(warmUpACache);
        referenceDataService.saveAClientInfo6(warmUpACache);
        referenceDataService.saveAClientInfo3(warmUpACache);
        log.info("Save last A record: {} as {}", currentNumber, totalCount);
    }

    @Override
    public void saveWarmUpB(BWarmupMessage BWarmupMessage) {
        WarmUpBCache warmUpBCache = converterKeeper.getWarmUpBConverter().prepareWarmUpBCache(BWarmupMessage);
        Integer totalCount = BWarmupMessage.getHeaders().getWarmUpCacheTotalCount();
        Integer currentNumber = BWarmupMessage.getHeaders().getWarmUpCacheCurrentNumber();

        referenceDataService.saveBClientInfo1(warmUpBCache);
        referenceDataService.saveBClientInfo2(warmUpBCache);
        referenceDataService.saveBClientInfo4(warmUpBCache);
        referenceDataService.saveBClientInfo5(warmUpBCache);
        referenceDataService.saveBClientInfo3(warmUpBCache);
        log.info("Save last B record: {} as {}", currentNumber, totalCount);
    }
}
