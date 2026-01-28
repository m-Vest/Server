CREATE TABLE asset_transactions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT,
    user_id BIGINT,
    stock_code VARCHAR(255),
    transaction_type VARCHAR(255),
    price DECIMAL(19, 4),
    quantity INT,
    cash_change DECIMAL(19, 4),
    created_at DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uk_asset_tx_order_id (order_id)
);
