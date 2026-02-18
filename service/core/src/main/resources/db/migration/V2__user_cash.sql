CREATE TABLE IF NOT EXISTS user_cash (
    user_id BIGINT NOT NULL,
    balance DECIMAL(19, 4),
    updated_at DATETIME,
    PRIMARY KEY (user_id),
    CONSTRAINT fk_user_cash_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
);
