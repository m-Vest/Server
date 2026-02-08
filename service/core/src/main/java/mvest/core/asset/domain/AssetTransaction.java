package mvest.core.asset.domain;

import mvest.common.event.payload.AssetChangeEventPayload;
import mvest.common.event.payload.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AssetTransaction {

    private final Long id;
    private final String orderId;
    private final Long userId;
    private final String stockCode;
    private final String transactionType;
    private final BigDecimal price;
    private final int quantity;
    private final BigDecimal cashChange;
    private final LocalDateTime createdAt;

    public AssetTransaction(Long id,
                            String orderId,
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

    public String getOrderId() {
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

    public static AssetTransaction initialDeposit(Long userId, BigDecimal amount) {
        return new AssetTransaction(
                null,
                null,
                userId,
                null,
                "INITIAL_DEPOSIT",
                null,
                0,
                amount,
                null
        );
    }

    public static AssetTransaction fromOrder(AssetChangeEventPayload payload) {

        BigDecimal amount =
                payload.getPrice().multiply(BigDecimal.valueOf(payload.getQuantity()));

        BigDecimal cashChange =
                payload.getOrderType() == OrderType.BUY
                        ? amount.negate()
                        : amount;

        return new AssetTransaction(
                null,
                payload.getOrderId(),
                payload.getUserId(),
                payload.getStockCode(),
                payload.getOrderType().name(),
                payload.getPrice(),
                payload.getQuantity(),
                cashChange,
                payload.getOccurredAt()
        );
    }
}
