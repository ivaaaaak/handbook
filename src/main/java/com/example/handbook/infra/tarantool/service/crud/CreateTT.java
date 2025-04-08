package com.example.handbook.infra.tarantool.service.crud;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import com.example.handbook.infra.tarantool.dao.InsertTarantoolDAO;
import com.example.handbook.infra.tarantool.entity.*;


@Log4j2
@Getter
@Repository
@AllArgsConstructor
public class CreateTT {

    private final InsertTarantoolDAO insertTarantoolDAO;

    public void saveClientInfo1(ClientInfo1 clientInfo1) {
        insertTarantoolDAO.insertClientInfo1(clientInfo1);
    }

    public void saveClientInfo4(ClientInfo4 clientInfo4) {
        insertTarantoolDAO.insertClientInfo4(clientInfo4);
    }

    public void saveClientInfo3(ClientInfo3 clientInfo3) {
        insertTarantoolDAO.insertClientInfo3(clientInfo3);
    }

    public void saveClientInfo6(ClientInfo6 ClientInfo6) {
        insertTarantoolDAO.insertClientInfo6(ClientInfo6);
    }

    public void saveClientInfo2(ClientInfo2 clientInfo2) {
        insertTarantoolDAO.insertClientInfo2(clientInfo2);
    }

    public void saveClientInfo5(ClientInfo5 clientInfo5) {
        insertTarantoolDAO.insertClientInfo5(clientInfo5);
    }
}
