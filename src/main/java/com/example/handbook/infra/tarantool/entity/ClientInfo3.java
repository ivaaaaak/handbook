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
public class ClientInfo3 implements TarantoolTupleMapper {

    private UUID id;
    private Integer uniqueValue345;
    private String uniqueValue1;
    private Instant insTs;
    private Instant updTs;

    @Override
    public TarantoolTuple getTuple(TarantoolTupleFactory tupleFactory) {
        return tupleFactory.create(
                this.id,
                this.uniqueValue345,
                this.uniqueValue1,
                Optional.ofNullable(this.insTs).map(DateUtil::toString).orElse(null),
                Optional.ofNullable(this.updTs).map(DateUtil::toString).orElse(null)
        );
    }

    public static List<ClientInfo3> buildResult(List<Object> rawClientInfo3s) {
        TarantoolTuple tuple;
        List<ClientInfo3> clientInfo3s = new ArrayList<>();
        if(!rawClientInfo3s.isEmpty()) {
            for(Object rawClientInfo3 : rawClientInfo3s) {
                tuple = (TarantoolTuple) rawClientInfo3;
                clientInfo3s.add(convertToObject(tuple));
            }
        }
        return clientInfo3s;
    }

    private static ClientInfo3 convertToObject(TarantoolTuple tuple) {
        return ClientInfo3.builder()
                .id(tuple.getUUID(0))
                .uniqueValue345(tuple.getInteger(1))
                .uniqueValue1(tuple.getString(2))
                .insTs(Optional.ofNullable(tuple.getString(3)).map(DateUtil::toInstant).orElse(null))
                .updTs(Optional.ofNullable(tuple.getString(4)).map(DateUtil::toInstant).orElse(null))
                .build();
    }
}
