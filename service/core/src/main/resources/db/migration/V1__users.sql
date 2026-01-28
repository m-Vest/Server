CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    platform VARCHAR(255),
    platform_id VARCHAR(255),
    user_name VARCHAR(255),
    birth DATE,
    created_at DATETIME,
    updated_at DATETIME,
    PRIMARY KEY (user_id)
);