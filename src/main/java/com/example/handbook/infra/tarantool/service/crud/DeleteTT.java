package com.example.handbook.infra.tarantool.service.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.example.handbook.infra.tarantool.entity.ClientInfo1;
import com.example.handbook.infra.tarantool.dao.DeleteTarantoolDAO;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class DeleteTT {
    
    private final DeleteTarantoolDAO deleteTarantoolDAO;

    public List<ClientInfo1> deleteClientInfo1sByvalue2(String value2) {
        try {
            return deleteTarantoolDAO.deleteClientInfo1Byvalue2(value2);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            return List.of();
        }
    }

    public List<ClientInfo1> deleteBClientInfo1sBy(String notifyvalue2, String ClientInfo1Num) {
        try {
            return deleteTarantoolDAO.deleteClientInfo1ForB(notifyvalue2, ClientInfo1Num);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            return List.of();
        }
    }

    public void deleteAllEntriesByUniqueValue1(List<ClientInfo1> deleteClientInfo1s) {
        for (ClientInfo1 clientInfo1 : deleteClientInfo1s) {
            log.info("Remove ClientInfo1 with uniqueValue1 is {}", clientInfo1.getUniqueValue1());
            deleteClientInfo2(clientInfo1.getUniqueValue1());
            deleteClientInfo4(clientInfo1.getUniqueValue1());
            deleteClientInfo5(clientInfo1.getUniqueValue1());
            deleteClientInfo3(clientInfo1.getUniqueValue1());
            deleteClientInfo6(clientInfo1.getUniqueValue1());
        }
    }

    public void deleteClientInfo2(String uniqueValue1) {
        try {
            deleteTarantoolDAO.deleteClientInfo2(uniqueValue1);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    public void deleteClientInfo4(String uniqueValue1) {
        try {
            deleteTarantoolDAO.deleteClientInfo4(uniqueValue1);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    public void deleteClientInfo5(String uniqueValue1) {
        try {
            deleteTarantoolDAO.deleteClientInfo5(uniqueValue1);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    public void deleteClientInfo3(String uniqueValue1) {
        try {
            deleteTarantoolDAO.deleteClientInfo3(uniqueValue1);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    public void deleteClientInfo6(String uniqueValue1) {
        try {
            deleteTarantoolDAO.deleteClientInfo6(uniqueValue1);
        } catch (Exception ex) {
            log.error(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }
}
