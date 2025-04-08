package com.example.handbook.domain.interactor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.handbook.domain.InputBoundary;
import com.example.handbook.domain.model.BWarmupMessage;
import com.example.handbook.domain.dto.InputBoundaryRq;
import com.example.handbook.domain.manager.DomainManager;
import com.example.handbook.domain.model.AWarmupMessage;
import com.example.handbook.domain.model.BNotifyMessage;
import com.example.handbook.domain.model.ANotifyMessage;

@Log4j2
@Lazy(value = false)
@Component
@AllArgsConstructor
public class RegisterInteractor implements InputBoundary {

    private final DomainManager domainManager;
    private ObjectMapper objectMapper;

    @Override
    public void create(InputBoundaryRq inputBoundaryRq) {
        switch (inputBoundaryRq.getTargetTopic()) {
            case InputBoundaryRq.WARM_UP_A -> warmUpAProcessing(inputBoundaryRq);
            case InputBoundaryRq.WARM_UP_B -> warmUpBProcessing(inputBoundaryRq);
            case InputBoundaryRq.NOTIFY_A -> changedNotifyAProcessing(inputBoundaryRq);
            case InputBoundaryRq.NOTIFY_B -> changedNotifyBProcessing(inputBoundaryRq);
        }
    }

    protected void warmUpAProcessing(InputBoundaryRq inputBoundaryRq) {
        try {
            AWarmupMessage aWarmupMessage = objectMapper.readValue(inputBoundaryRq.getMessage(), AWarmupMessage.class);
            aWarmupMessage.setHeaders(AWarmupMessage.Headers.of(inputBoundaryRq.getHeaders()));
            domainManager.saveWarmUpA(aWarmupMessage);

        } catch(Exception ex) {
            log.warn("Error message: {}", inputBoundaryRq.getMessage());
            log.error(ex);
        }
    }

    protected void warmUpBProcessing(InputBoundaryRq inputBoundaryRq) {
        try {
            BWarmupMessage bWarmupMessage = objectMapper.readValue(inputBoundaryRq.getMessage(), BWarmupMessage.class);
            bWarmupMessage.setHeaders(BWarmupMessage.Headers.of(inputBoundaryRq.getHeaders()));
            domainManager.saveWarmUpB(bWarmupMessage);

        } catch(Exception ex) {
            log.warn("Error message: {}", inputBoundaryRq.getMessage());
            log.error(ex);
        }
    }

    protected void changedNotifyAProcessing(InputBoundaryRq inputBoundaryRq) {
        try {
            ANotifyMessage aNotifyMessage = objectMapper.readValue(inputBoundaryRq.getMessage(), ANotifyMessage.class);
            domainManager.saveNotifyA(aNotifyMessage);

        } catch(Exception ex) {
            log.warn("Error message: {}", inputBoundaryRq.getMessage());
            log.error(ex);
        }
    }

    protected void changedNotifyBProcessing(InputBoundaryRq inputBoundaryRq) {
        try {
            BNotifyMessage bNotifyMessage = objectMapper.readValue(inputBoundaryRq.getMessage(), BNotifyMessage.class);
            domainManager.saveNotifyB(bNotifyMessage);

        } catch(Exception ex) {
            log.warn("Error message: {}", inputBoundaryRq.getMessage());
            log.error(ex);
        }
    }
}
