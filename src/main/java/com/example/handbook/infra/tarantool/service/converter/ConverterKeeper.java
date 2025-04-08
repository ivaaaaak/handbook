package com.example.handbook.infra.tarantool.service.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
@AllArgsConstructor
public class ConverterKeeper {

    private final WarmUpAConverter warmUpAConverter;
    private final WarmUpBConverter warmUpBConverter;
    private final NotifyAConverter notifyAConverter;
    private final NotifyBConverter notifyBConverter;
}
