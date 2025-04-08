package com.example.handbook.infra.tarantool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import com.example.handbook.infra.tarantool.entity.*;
import com.example.handbook.infra.tarantool.entity.ClientInfo1;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NotifyACache {
    private String value2;
    private List<ClientInfo1> clientInfo1s = new ArrayList<>();
    private List<ClientInfo2> clientInfo2s = new ArrayList<>();
    private List<ClientInfo5> clientInfo5s = new ArrayList<>();
    private List<ClientInfo4> clientInfo4s = new ArrayList<>();
    private List<ClientInfo3> clientInfo3s = new ArrayList<>();
    private ClientInfo6 ClientInfo6;
    private List<Attribute> changeAttributes = new ArrayList<>();
}
