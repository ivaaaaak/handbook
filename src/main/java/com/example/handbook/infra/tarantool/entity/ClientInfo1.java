package com.example.handbook.infra.tarantool.entity;

import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.api.tuple.TarantoolTupleFactory;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id", "insTs"})
public class ClientInfo1 implements TarantoolTupleMapper {

    private UUID id;
    private Long value1;
    private String value2;
    private Boolean value3;
    private String uniqueValue1;
    private Instant insTs;
    private Instant updTs;

    @Override
    public TarantoolTuple getTuple(TarantoolTupleFactory tupleFactory) {
        return tupleFactory.create(
                this.id,
                this.value1,
                this.value2,
                this.value3,
                this.uniqueValue1,
                Optional.ofNullable(this.insTs).map(DateUtil::toString).orElse(null),
                Optional.ofNullable(this.updTs).map(DateUtil::toString).orElse(null)
        );
    }

    public static List<ClientInfo1> buildResult(List<Object> rawClientInfo1s) {
        TarantoolTuple tuple;
        List<ClientInfo1> clientInfo1s = new ArrayList<>();
        if(!rawClientInfo1s.isEmpty()) {
            for(Object rawClientInfo1 : rawClientInfo1s) {
                tuple = (TarantoolTuple) rawClientInfo1;
                clientInfo1s.add(convertToObject(tuple));
            }
        }
        return clientInfo1s;
    }

    private static ClientInfo1 convertToObject(TarantoolTuple tuple) {
        return ClientInfo1.builder()
                .id(tuple.getUUID(0))
                .value1(tuple.getLong(1))
                .value2(tuple.getString(2))
                .value3(tuple.getBoolean(3))
                .uniqueValue1(tuple.getString(4))
                .insTs(Optional.ofNullable(tuple.getString(5)).map(DateUtil::toInstant).orElse(null))
                .updTs(Optional.ofNullable(tuple.getString(6)).map(DateUtil::toInstant).orElse(null))
                .build();
    }
}
