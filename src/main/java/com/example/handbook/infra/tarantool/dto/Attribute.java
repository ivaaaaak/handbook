package com.example.handbook.infra.tarantool.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Attribute {

    private String attribute;
    private String changeType;
    private String key;
    private List<Field> fields;

    @Log4j2
    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    public static class Field {
        private String fieldName;
        private String oldValue;
        private String newValue;
    }
}
