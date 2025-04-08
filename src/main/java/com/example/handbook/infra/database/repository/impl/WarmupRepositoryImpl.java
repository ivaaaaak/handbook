package com.example.handbook.infra.database.repository.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.handbook.infra.database.entity.BufferNotify;
import com.example.handbook.infra.database.repository.WarmupRepository;
import com.example.handbook.infra.database.entity.WarmUp;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Log4j2
@Repository
@AllArgsConstructor
public class WarmupRepositoryImpl implements WarmupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insertWarmUp(WarmUp warmUp) {
        jdbcTemplate.update(
                "INSERT INTO warmup (channel_type, warmup_status, total_count, ins_ts, upd_ts) VALUES (?,?,?,?,?)",
                warmUp.getChannelType(),
                warmUp.getWarmupStatus(),
                warmUp.getTotalCount(),
                warmUp.getInsTs(),
                warmUp.getUpdTs()
        );
    }

    @Override
    public void updateWarmupCompleted(String channelType, int totalCount) {
        jdbcTemplate.update(
                "UPDATE warmup SET warmup_status = ?, total_count = ?, upd_ts = ? where channel_type = ? ",
                false, totalCount, Timestamp.from(Instant.now()), channelType);
    }

    @Override
    public List<WarmUp> getWarmUp(String channelType) {
        try {
            return jdbcTemplate.query("SELECT * FROM warmup WHERE channel_type = ?",
                    new Object[]{channelType},
                    (rs, rowNum) -> WarmUp.builder()
                            .id(rs.getLong("id"))
                            .channelType(rs.getString("channel_type"))
                            .warmupStatus(rs.getBoolean("warmup_status"))
                            .totalCount(rs.getInt("total_count"))
                            .insTs(rs.getTimestamp("ins_ts"))
                            .updTs(rs.getTimestamp("upd_ts"))
                            .build());
        } catch (Exception ex) {
            log.error(ex);
            return List.of();
        }
    }

    @Override
    public void insertBufferNotify(BufferNotify bufferNotify) {
        jdbcTemplate.update(
                "INSERT INTO buffer_notify (message, type, work_status) VALUES (?,?,?)",
                bufferNotify.getMessage(),
                bufferNotify.getType(),
                bufferNotify.getWorkStatus()
        );
    }

    @Override
    public List<BufferNotify> getBufferNotifyBatch(int batchSize) {
        return jdbcTemplate.query("SELECT * FROM buffer_notify WHERE work_status = ? ORDER BY id DESC LIMIT ? FOR UPDATE",
                new Object[]{false, batchSize},
                (rs, rowNum) -> BufferNotify.builder()
                        .id(rs.getLong("id"))
                        .message(rs.getBytes("message"))
                        .type(rs.getString("type"))
                        .workStatus(rs.getBoolean("work_status"))
                        .build());
    }

    @Override
    public void updateBufferNotify(Long id, Boolean workStatus) {
        jdbcTemplate.update(
                "UPDATE buffer_notify SET work_status = ? WHERE id = ?",
                workStatus, id
        );
    }

    @Override
    public void truncateBufferNotify() {
        jdbcTemplate.update("TRUNCATE TABLE buffer_notify");
    }

}
