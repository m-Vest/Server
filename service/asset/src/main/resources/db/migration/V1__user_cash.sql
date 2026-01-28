CREATE TABLE user_cash (
    user_id BIGINT NOT NULL,
    balance DECIMAL(19, 4),
    updated_at DATETIME,
    PRIMARY KEY (user_id)
);
