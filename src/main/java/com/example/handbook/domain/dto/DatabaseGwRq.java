package com.example.handbook.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatabaseGwRq {

    private byte[] message;
    private String messageType;

}
