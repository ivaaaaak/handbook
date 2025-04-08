package com.example.handbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.handbook.infra.database.entity.BufferNotify;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseGwRs {

    private List<BufferNotify> packNotifies;
    private boolean needWarmUpStatus;
    private boolean warmupCompleted;

}
