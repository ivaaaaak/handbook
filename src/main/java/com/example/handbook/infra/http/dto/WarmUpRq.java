package com.example.handbook.infra.http.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarmUpRq {
    private String profileStatus;
    private String clientType;
    private String ClientInfo1Type;
    private String requestId;
    private List<String> value2s;
}
