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
public class ClientInfo6 implements TarantoolTupleMapper {

    private UUID id;
    private String uniqueValue1;
    private Boolean value1;
    private String value2;
    private Double value3;
    private Instant insTs;
    private Instant updTs;

    @Override
    public TarantoolTuple getTuple(TarantoolTupleFactory tupleFactory) {
        return tupleFactory.create(
                this.id,
                this.uniqueValue1,
                this.value1,
                this.value2,
                this.value3,
                Optional.ofNullable(this.insTs).map(DateUtil::toString).orElse(null),
                Optional.ofNullable(this.updTs).map(DateUtil::toString).orElse(null)
        );
    }

    public static List<ClientInfo6> buildResult(List<Object> rawClientInfo6List) {
        TarantoolTuple tuple;
        List<ClientInfo6> ClientInfo6List = new ArrayList<>();
        if(!rawClientInfo6List.isEmpty()) {
            for(Object rawClientInfo6 : rawClientInfo6List) {
                tuple = (TarantoolTuple) rawClientInfo6;
                ClientInfo6List.add(convertToObject(tuple));
            }
        }
        return ClientInfo6List;
    }

    private static ClientInfo6 convertToObject(TarantoolTuple tuple) {
        return ClientInfo6.builder()
                .id(tuple.getUUID(0))
                .uniqueValue1(tuple.getString(1))
                .value1(tuple.getBoolean(2))
                .value2(tuple.getString(3))
                .value3(tuple.getDouble(4))
                .insTs(Optional.ofNullable(tuple.getString(5)).map(DateUtil::toInstant).orElse(null))
                .updTs(Optional.ofNullable(tuple.getString(6)).map(DateUtil::toInstant).orElse(null))
                .build();
    }
}
