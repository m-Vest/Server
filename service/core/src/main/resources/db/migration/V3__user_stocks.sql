CREATE TABLE user_stocks (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    stock_code VARCHAR(255),
    quantity INT,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_stock (user_id, stock_code),
    CONSTRAINT fk_user_assets_user
            FOREIGN KEY (user_id) REFERENCES users(user_id)
);
