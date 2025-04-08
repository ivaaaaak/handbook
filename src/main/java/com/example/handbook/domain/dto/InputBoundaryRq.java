package com.example.handbook.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class InputBoundaryRq implements Serializable {

    public final static String WARM_UP_A = "W_A";
    public final static String WARM_UP_B = "W_B";
    public final static String NOTIFY_A = "N_A";
    public final static String NOTIFY_B = "N_B";

    private Map<String, byte[]> headers;
    private String message;
    private String targetTopic;
}
