CREATE TABLE outbox (
    outbox_id BIGINT NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    PRIMARY KEY (outbox_id)
);
