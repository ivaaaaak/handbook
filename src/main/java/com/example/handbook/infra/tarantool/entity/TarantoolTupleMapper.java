package com.example.handbook.infra.tarantool.entity;

import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.api.tuple.TarantoolTupleFactory;

public interface TarantoolTupleMapper extends TarantoolSpace {

    TarantoolTuple getTuple(TarantoolTupleFactory tupleFactory);
}
