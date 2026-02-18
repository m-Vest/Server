CREATE TABLE IF NOT EXISTS asset_daily_snapshot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    snapshot_date DATE NOT NULL,
    total_asset DECIMAL(19,4) NOT NULL,
    cash_amount DECIMAL(19,4) NOT NULL,
    stock_evaluation_amount DECIMAL(19,4) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    UNIQUE KEY uk_user_date (user_id, snapshot_date)
);
