package com.example.handbook.infra.http.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarmUpBRq {
    private String profileStatus;
    private String requestId;
    private List<String> value2s;
}
