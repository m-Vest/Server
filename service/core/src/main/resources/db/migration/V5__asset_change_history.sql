CREATE TABLE asset_change_history (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT,
    user_id BIGINT NOT NULL,
    stock_code VARCHAR(255),
    change_type VARCHAR(255),
    stock_change INT,
    cash_change DECIMAL(19, 4),
    created_at DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_asset_change_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
);
