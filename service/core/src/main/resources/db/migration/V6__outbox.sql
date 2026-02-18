create table IF NOT EXISTS outbox (
    outbox_id bigint not null primary key,
    event_type varchar(100) not null,
    payload varchar(5000) not null,
    created_at datetime not null
);