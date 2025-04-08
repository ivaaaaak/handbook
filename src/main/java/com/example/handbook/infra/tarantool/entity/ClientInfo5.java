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
public class ClientInfo5 implements TarantoolTupleMapper {

    private UUID id;
    private Integer uniqueValue345;
    private String uniqueValue1;
    private String value1;
    private String value2;
    private Integer value3;
    private String uniqueValue2;
    private Instant insTs;
    private Instant updTs;

    @Override
    public TarantoolTuple getTuple(TarantoolTupleFactory tupleFactory) {
        return tupleFactory.create(this.id,
                this.uniqueValue345,
                this.uniqueValue1,
                this.value1,
                this.value2,
                this.value3,
                this.uniqueValue2,
                Optional.ofNullable(this.insTs).map(DateUtil::toString).orElse(null),
                Optional.ofNullable(this.updTs).map(DateUtil::toString).orElse(null)
        );
    }

    public static List<ClientInfo5> buildResult(List<Object> rawClientInfo4s) {
        TarantoolTuple tuple;
        List<ClientInfo5> clientInfo5s = new ArrayList<>();
        if(!rawClientInfo4s.isEmpty()) {
            for(Object rawClientInfo4 : rawClientInfo4s) {
                tuple = (TarantoolTuple) rawClientInfo4;
                clientInfo5s.add(convertToObject(tuple));
            }
        }
        return clientInfo5s;
    }

    private static ClientInfo5 convertToObject(TarantoolTuple tuple) {
        return ClientInfo5.builder()
                .id(tuple.getUUID(0))
                .uniqueValue345(tuple.getInteger(1))
                .uniqueValue1(tuple.getString(2))
                .value1(tuple.getString(3))
                .value2(tuple.getString(4))
                .value3(tuple.getInteger(5))
                .uniqueValue2(tuple.getString(6))
                .insTs(Optional.ofNullable(tuple.getString(7)).map(DateUtil::toInstant).orElse(null))
                .updTs(Optional.ofNullable(tuple.getString(8)).map(DateUtil::toInstant).orElse(null))
                .build();
    }
}
