package com.example.handbook.infra.database.entity;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class BufferNotify {

    private long id;
    private byte[] message;
    private String type;
    private Boolean workStatus;
}
