CREATE TABLE IF NOT EXISTS orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    stock_code VARCHAR(255),
    order_type VARCHAR(255),
    price DECIMAL(19, 4),
    quantity INT,
    status VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_order_id (order_id),
    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
);
