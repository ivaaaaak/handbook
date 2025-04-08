package com.example.handbook.infra.tarantool.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Pause {

    public static void operationalPause(long pause) {
        try {
            log.info("Operational pause: {}", pause);
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            log.error(e);
        }
    }
}
