package mvest.core.asset.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AssetChangeHistory {

    private final Long id;
    private final Long orderId;
    private final Long userId;
    private final String stockCode;
    private final String changeType;
    private final int stockChange;
    private final BigDecimal cashChange;
    private final LocalDateTime createdAt;

    public AssetChangeHistory(Long id,
                              Long orderId,
                              Long userId,
                              String stockCode,
                              String changeType,
                              int stockChange,
                              BigDecimal cashChange,
                              LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.stockCode = stockCode;
        this.changeType = changeType;
        this.stockChange = stockChange;
        this.cashChange = cashChange;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getChangeType() {
        return changeType;
    }

    public int getStockChange() {
        return stockChange;
    }

    public BigDecimal getCashChange() {
        return cashChange;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
