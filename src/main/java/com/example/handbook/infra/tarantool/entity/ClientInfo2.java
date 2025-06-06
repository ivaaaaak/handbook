package com.example.handbook.infra.tarantool.entity;

import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.api.tuple.TarantoolTupleFactory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import com.example.handbook.infra.tarantool.util.DateUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Getter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id", "insTs"})
public class ClientInfo2 implements TarantoolTupleMapper {

    private UUID id;
    private String uniqueValue1;
    private String uniqueValue2;
    private Boolean value1;
    private String value2;
    private Instant insTs;
    private Instant updTs;


    @Override
    public TarantoolTuple getTuple(TarantoolTupleFactory tupleFactory) {
        return tupleFactory.create(this.id,
                this.uniqueValue1,
                this.uniqueValue2,
                this.value1,
                this.value2,
                Optional.ofNullable(this.insTs).map(DateUtil::toString).orElse(null),
                Optional.ofNullable(this.updTs).map(DateUtil::toString).orElse(null)
        );
    }

    public static List<ClientInfo2> buildResult(List<Object> rawClientInfo2s) {
        TarantoolTuple tuple;
        List<ClientInfo2> clientInfo2s = new ArrayList<>();
        if(!rawClientInfo2s.isEmpty()) {
            for(Object rawClientInfo2 : rawClientInfo2s) {
                tuple = (TarantoolTuple) rawClientInfo2;
                clientInfo2s.add(convertToObject(tuple));
            }
        }
        return clientInfo2s;
    }

    private static ClientInfo2 convertToObject(TarantoolTuple tuple) {
        return ClientInfo2.builder()
                .id(tuple.getUUID(0))
                .uniqueValue1(tuple.getString(1))
                .uniqueValue2(tuple.getString(2))
                .value1(tuple.getBoolean(3))
                .value2(tuple.getString(4))
                .insTs(Optional.ofNullable(tuple.getString(5)).map(DateUtil::toInstant).orElse(null))
                .updTs(Optional.ofNullable(tuple.getString(6)).map(DateUtil::toInstant).orElse(null))
                .build();
    }
}
