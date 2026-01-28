package mvest.asset.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AssetTransaction {

    private final Long id;
    private final Long orderId;
    private final Long userId;
    private final String stockCode;
    private final String transactionType;
    private final BigDecimal price;
    private final int quantity;
    private final BigDecimal cashChange;
    private final LocalDateTime createdAt;

    public AssetTransaction(Long id,
                            Long orderId,
                            Long userId,
                            String stockCode,
                            String transactionType,
                            BigDecimal price,
                            int quantity,
                            BigDecimal cashChange,
                            LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.stockCode = stockCode;
        this.transactionType = transactionType;
        this.price = price;
        this.quantity = quantity;
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

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getCashChange() {
        return cashChange;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
