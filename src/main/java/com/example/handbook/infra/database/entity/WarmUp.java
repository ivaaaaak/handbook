package com.example.handbook.infra.database.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;

@Log4j2
@Getter
@Setter
@Builder
@ToString
public class WarmUp {

    private Long id;
    private String channelType;
    private Boolean warmupStatus;
    private Integer totalCount;
    private Timestamp insTs;
    private Timestamp updTs;
}
