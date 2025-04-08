package com.example.handbook.domain.manager;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import com.example.handbook.config.ApplicationProperties;
import com.example.handbook.domain.DatabaseGw;
import com.example.handbook.domain.InMemoryPlatformGw;
import com.example.handbook.domain.TransferGw;

import java.util.concurrent.ThreadLocalRandom;

import static com.example.handbook.domain.common.Constants.*;
import static com.example.handbook.infra.tarantool.util.Pause.operationalPause;

@Log4j2
@Component
@AllArgsConstructor
public class WarmupRequestManager {

    private final TransferGw transferGw;
    private final ApplicationProperties appProp;
    private final InMemoryPlatformGw inMemoryPlatformGw;
    private final DatabaseGw databaseGw;

    @PostConstruct
    public void initWarmUp() {
        if (!inMemoryPlatformGw.checkTarantoolConnection()) {
            log.error("Connection error with tarantool");
        } else {
            new Thread(this::warmupLogic).start();
        }
    }

    private void warmupLogic() {
        operationalPause(getRandomValue(appProp.getRqDurationStartRange(), appProp.getRqDurationEndRange()));

        boolean aNeedWarmup = databaseGw.launchControl(A).isNeedWarmUpStatus();
        boolean bNeedWarmup = databaseGw.launchControl(B).isNeedWarmUpStatus();

        log.info("Need warmup a: [{}], need warmup b: [{}]", aNeedWarmup, bNeedWarmup);

        if (aNeedWarmup) {
            transferGw.sendWarmUpARq();
        }
        if (bNeedWarmup) {
            operationalPause(appProp.getTimeoutBetweenRequests());
            transferGw.sendWarmUpBRq();
        }
    }

    private long getRandomValue(int min, int max) {
        int slippage = 100;
        long refNumber = ThreadLocalRandom
                .current()
                .nextLong((min + slippage), (max + 1 + slippage));
        long drivenNumber = ThreadLocalRandom
                .current()
                .nextLong(min, max + 1);
        if(refNumber < drivenNumber) {
            return drivenNumber - refNumber;
        } else {
            return refNumber - drivenNumber;
        }
    }
}
