package com.example.handbook.infra.tarantool.dao;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.api.space.TarantoolSpaceOperations;
import com.example.handbook.infra.tarantool.entity.ClientInfo1;
import com.example.handbook.infra.tarantool.entity.ClientInfo3;
import com.example.handbook.infra.tarantool.entity.ClientInfo2;
import com.example.handbook.infra.tarantool.entity.ClientInfo5;
import com.example.handbook.infra.tarantool.entity.ClientInfo4;
import com.example.handbook.infra.tarantool.entity.ClientInfo6;

import static com.example.handbook.infra.tarantool.dao.TarantoolDaoImpl.*;

@Log4j2
@Service
@AllArgsConstructor
public class InsertTarantoolDAO {

    private final TarantoolDaoImpl tarantoolDao;

    public void insertClientInfo1(ClientInfo1 clientInfo1) {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO1_TB_NAME);
        space.insert(clientInfo1.getTuple(tarantoolTupleFactory));
    }

    public void insertClientInfo4(ClientInfo4 clientInfo4) {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO4_TB_NAME);
        space.insert(clientInfo4.getTuple(tarantoolTupleFactory));
    }

    public void insertClientInfo3(ClientInfo3 clientInfo3) {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO3_TB_NAME);
        space.insert(clientInfo3.getTuple(tarantoolTupleFactory));
    }

    public void insertClientInfo6(ClientInfo6 ClientInfo6) {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO6_TB_NAME);
        space.insert(ClientInfo6.getTuple(tarantoolTupleFactory));
    }

    public void insertClientInfo2(ClientInfo2 clientInfo2) {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO2_TB_NAME);
        space.insert(clientInfo2.getTuple(tarantoolTupleFactory));
    }

    public void insertClientInfo5(ClientInfo5 clientInfo5) {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO5_TB_NAME);
        space.insert(clientInfo5.getTuple(tarantoolTupleFactory));
    }
}
