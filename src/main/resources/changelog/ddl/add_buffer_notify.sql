create table if not exists buffer_notify(
      id               bigserial       primary key,
      message          bytea           not null,
      type             varchar(100)    not null,
      work_status      boolean         not null
);