package com.example.handbook.infra.tarantool.service.crud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import com.example.handbook.infra.tarantool.dao.SelectTarantoolDAO;

@Log4j2
@Getter
@Repository
@AllArgsConstructor
public class ReadTT {

    private final SelectTarantoolDAO selectTarantoolDAO;

    public boolean checkTarantool() {
        try {
            String pingResult = selectTarantoolDAO.tarantoolPing();
            log.info("Ping tarantool result: {}", pingResult);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
