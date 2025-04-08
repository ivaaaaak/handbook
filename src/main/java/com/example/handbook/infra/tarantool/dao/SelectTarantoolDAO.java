package com.example.handbook.infra.tarantool.dao;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
@Service
@AllArgsConstructor
public class SelectTarantoolDAO {

    private final TarantoolDaoImpl tarantoolDao;

    private static final String PING_QUERY = "return box.info()";

    public String tarantoolPing() throws ExecutionException, InterruptedException {
        CompletableFuture<List<?>> pingResult = tarantoolDao.getTarantoolClient().eval(PING_QUERY);
        return pingResult.get().getFirst().toString();
    }
}
