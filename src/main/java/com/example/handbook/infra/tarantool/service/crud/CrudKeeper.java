package com.example.handbook.infra.tarantool.service.crud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
@AllArgsConstructor
public class CrudKeeper {

    private final CreateTT createTT;
    private final ReadTT readTT;
    private final DeleteTT deleteTT;
}
