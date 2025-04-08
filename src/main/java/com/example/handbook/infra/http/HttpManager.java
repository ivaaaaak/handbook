package com.example.handbook.infra.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import com.example.handbook.domain.TransferGw;
import com.example.handbook.infra.http.service.RestRequester;

@Log4j2
@Component
@AllArgsConstructor
public class HttpManager implements TransferGw {

    private final RestRequester restRequester;

    @Override
    public void sendWarmUpARq() {
        try {
            restRequester.sendWarmUpARq();
        } catch (JsonProcessingException e) {
            log.error(e);
        }
    }

    @Override
    public void sendWarmUpBRq() {
        try {
            restRequester.sendWarmUpBRq();
        } catch (JsonProcessingException e) {
            log.error(e);
        }
    }
}
