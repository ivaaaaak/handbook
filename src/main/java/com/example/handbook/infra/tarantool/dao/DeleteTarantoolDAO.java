package com.example.handbook.infra.tarantool.dao;

import java.util.List;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import io.tarantool.driver.api.TarantoolResult;
import java.util.concurrent.ExecutionException;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.api.conditions.Conditions;
import com.example.handbook.infra.tarantool.entity.*;
import io.tarantool.driver.api.space.TarantoolSpaceOperations;

import static com.example.handbook.infra.tarantool.dao.TarantoolDaoImpl.*;

@Log4j2
@Service
@AllArgsConstructor
public class DeleteTarantoolDAO {

    private static final String UNIQUE_VALUE1_FIELD_NAME = "uniqueValue1";
    private static final String VALUE2_FIELD_NAME = "value2";
    private static final String ID_FIELD_NAME = "id";

    private final TarantoolDaoImpl tarantoolDao;

    public List<ClientInfo1> deleteClientInfo1Byvalue2(String value2) throws ExecutionException, InterruptedException {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO1_TB_NAME);
        List<ClientInfo1> ClientInfo1sForDelete = ClientInfo1.buildResult(List.of(space.select(Conditions.equals(VALUE2_FIELD_NAME, value2)).get().toArray()));
        List<ClientInfo1> deletedClientInfo1s = new ArrayList<>();
        for (ClientInfo1 clientInfo1 : ClientInfo1sForDelete) {
            log.info("Remove ClientInfo1 with id {}", clientInfo1.getId());
            deletedClientInfo1s.addAll(ClientInfo1.buildResult(List.of(space.delete(Conditions.equals(ID_FIELD_NAME, clientInfo1.getId())).get().toArray())));
        }
        return deletedClientInfo1s;
    }

    public List<ClientInfo1> deleteClientInfo1ForB(String notifyvalue2, String ClientInfo1Num) throws ExecutionException, InterruptedException {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO1_TB_NAME);
        List<ClientInfo1> ClientInfo1sForDelete = ClientInfo1.buildResult(
                List.of(space.select(Conditions.equals(VALUE2_FIELD_NAME, notifyvalue2).andEquals(UNIQUE_VALUE1_FIELD_NAME, ClientInfo1Num)).get().toArray())
        );
        List<ClientInfo1> deletedBClientInfo1s = new ArrayList<>();
        for (ClientInfo1 clientInfo1 : ClientInfo1sForDelete) {
            log.info("[B] Remove ClientInfo1 with id = {} and uniqueValue1 = {}", clientInfo1.getId(), clientInfo1.getUniqueValue1());
            deletedBClientInfo1s.addAll(ClientInfo1.buildResult(List.of(space.delete(Conditions.equals(ID_FIELD_NAME, clientInfo1.getId())).get().toArray())));
        }
        return deletedBClientInfo1s;
    }

    public void deleteClientInfo2(String uniqueValue1) throws ExecutionException, InterruptedException {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO2_TB_NAME);
        List<ClientInfo2> ClientInfo2sForDelete = ClientInfo2.buildResult(List.of(space.select(Conditions.equals(UNIQUE_VALUE1_FIELD_NAME, uniqueValue1)).get().toArray()));
        for (ClientInfo2 clientInfo2 : ClientInfo2sForDelete) {
            log.info("Remove ClientInfo2 with id {}", clientInfo2.getId());
            space.delete(Conditions.equals(ID_FIELD_NAME, clientInfo2.getId()));
        }
    }

    public void deleteClientInfo4(String uniqueValue1) throws ExecutionException, InterruptedException {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO4_TB_NAME);
        List<ClientInfo5> ClientInfo4sForDelete = ClientInfo5.buildResult(List.of(space.select(Conditions.equals(UNIQUE_VALUE1_FIELD_NAME, uniqueValue1)).get().toArray()));
        for (ClientInfo5 clientInfo5 : ClientInfo4sForDelete) {
            log.info("Remove ClientInfo4 with id {}", clientInfo5.getId());
            space.delete(Conditions.equals(ID_FIELD_NAME, clientInfo5.getId()));
        }
    }

    public void deleteClientInfo6(String uniqueValue1) throws ExecutionException, InterruptedException {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO6_TB_NAME);
        List<ClientInfo6> ClientInfo6Delete = ClientInfo6.buildResult(List.of(space.select(Conditions.equals(UNIQUE_VALUE1_FIELD_NAME, uniqueValue1)).get().toArray()));
        for (ClientInfo6 ClientInfo4 : ClientInfo6Delete) {
            log.info("Remove ClientInfo4 with id {}", ClientInfo4.getId());
            space.delete(Conditions.equals(ID_FIELD_NAME, ClientInfo4.getId()));
        }
    }

    public void deleteClientInfo5(String uniqueValue1) throws ExecutionException, InterruptedException {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO5_TB_NAME);
        List<ClientInfo4> ClientInfo5sForDelete = ClientInfo4.buildResult(List.of(space.select(Conditions.equals(UNIQUE_VALUE1_FIELD_NAME, uniqueValue1)).get().toArray()));
        for (ClientInfo4 clientInfo4 : ClientInfo5sForDelete) {
            log.info("Remove ClientInfo5 with id {}", clientInfo4.getId());
            space.delete(Conditions.equals(ID_FIELD_NAME, clientInfo4.getId()));
        }
    }

    public void deleteClientInfo3(String uniqueValue1) throws ExecutionException, InterruptedException {
        TarantoolSpaceOperations<TarantoolTuple, TarantoolResult<TarantoolTuple>> space = tarantoolDao.getTarantoolClient().space(CLIENT_INFO3_TB_NAME);
        List<ClientInfo3> ClientInfo3sForDelete = ClientInfo3.buildResult(List.of(space.select(Conditions.equals(UNIQUE_VALUE1_FIELD_NAME, uniqueValue1)).get().toArray()));
        for (ClientInfo3 clientInfo3 : ClientInfo3sForDelete) {
            log.info("Remove ClientInfo3 with id {}", clientInfo3.getId());
            space.delete(Conditions.equals(ID_FIELD_NAME, clientInfo3.getId()));
        }
    }
}
