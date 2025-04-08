package com.example.handbook.infra.tarantool.dao;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.DefaultTarantoolTupleFactory;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.api.tuple.TarantoolTupleFactory;
import io.tarantool.driver.mappers.factories.DefaultMessagePackMapperFactory;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Getter
@RequiredArgsConstructor
public class TarantoolDaoImpl {

    public static final String CLIENT_INFO1_TB_NAME = "client_info1";
    public static final String CLIENT_INFO2_TB_NAME = "client_info2";
    public static final String CLIENT_INFO3_TB_NAME = "client_info3";
    public static final String CLIENT_INFO4_TB_NAME = "client_info4";
    public static final String CLIENT_INFO5_TB_NAME = "client_info5";
    public static final String CLIENT_INFO6_TB_NAME = "client_info6";

    public static final TarantoolTupleFactory tarantoolTupleFactory = new DefaultTarantoolTupleFactory(DefaultMessagePackMapperFactory.getInstance().defaultComplexTypesMapper());
    private final TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient;

    @NonNull
    private List<String> spaces;

    @PostConstruct
    private void initSpaces() {
        spaces = new ArrayList<>();
        spaces.add(prepareSpaceName(CLIENT_INFO1_TB_NAME));
        spaces.add(prepareSpaceName(CLIENT_INFO2_TB_NAME));
        spaces.add(prepareSpaceName(CLIENT_INFO3_TB_NAME));
        spaces.add(prepareSpaceName(CLIENT_INFO4_TB_NAME));
        spaces.add(prepareSpaceName(CLIENT_INFO5_TB_NAME));
        spaces.add(prepareSpaceName(CLIENT_INFO6_TB_NAME));
    }

    private String prepareSpaceName(String spaceName) {
        return spaceName.replaceAll("_", "").toLowerCase();
    }
}
