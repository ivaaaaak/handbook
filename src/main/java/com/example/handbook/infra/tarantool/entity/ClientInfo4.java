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
public class ClientInfo4 implements TarantoolTupleMapper {

    private UUID id;
    private Integer uniqueValue345;
    private String uniqueValue1;
    private String value1;
    private Instant insTs;
    private Instant updTs;

    @Override
    public TarantoolTuple getTuple(TarantoolTupleFactory tupleFactory) {
        return tupleFactory.create(
                this.id,
                this.uniqueValue345,
                this.uniqueValue1,
                this.value1,
                Optional.ofNullable(this.insTs).map(DateUtil::toString).orElse(null),
                Optional.ofNullable(this.updTs).map(DateUtil::toString).orElse(null)
        );
    }

    public static List<ClientInfo4> buildResult(List<Object> rawClientInfo5s) {
        TarantoolTuple tuple;
        List<ClientInfo4> clientInfo4s = new ArrayList<>();
        if(!rawClientInfo5s.isEmpty()) {
            for(Object rawClientInfo5 : rawClientInfo5s) {
                tuple = (TarantoolTuple) rawClientInfo5;
                clientInfo4s.add(convertToObject(tuple));
            }
        }
        return clientInfo4s;
    }

    private static ClientInfo4 convertToObject(TarantoolTuple tuple) {
        return ClientInfo4.builder()
                .id(tuple.getUUID(0))
                .uniqueValue345(tuple.getInteger(1))
                .uniqueValue1(tuple.getString(2))
                .value1(tuple.getString(3))
                .insTs(Optional.ofNullable(tuple.getString(4)).map(DateUtil::toInstant).orElse(null))
                .updTs(Optional.ofNullable(tuple.getString(5)).map(DateUtil::toInstant).orElse(null))
                .build();
    }
}
