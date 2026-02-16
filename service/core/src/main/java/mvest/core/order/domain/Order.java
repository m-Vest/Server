package mvest.core.order.domain;

import mvest.common.event.payload.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {

    private final Long id;
    private final String orderId;
    private final Long userId;
    private final String stockCode;
    private final OrderType orderType;
    private final BigDecimal price;
    private final int quantity;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Order(Long id,
                 String orderId,
                 Long userId,
                 String stockCode,
                 OrderType orderType,
                 BigDecimal price,
                 int quantity,
                 OrderStatus status,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.stockCode = stockCode;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public OrderType getOrderType() {
        return orderType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static Order create(String orderId,
                               Long userId,
                               String stockCode,
                               OrderType orderType,
                               BigDecimal price,
                               int quantity,
                               OrderStatus status) {

        return new Order(
                null,
                orderId,
                userId,
                stockCode,
                orderType,
                price,
                quantity,
                status,
                null,
                null
        );
    }
}
