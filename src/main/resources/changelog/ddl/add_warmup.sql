create table if not exists warmup(
    id                  bigserial       primary key,
    channel_type        varchar(100)    not null,
    warmup_status       boolean         not null,
    total_count         integer,
    ins_ts              timestamp,
    upd_ts              timestamp
);