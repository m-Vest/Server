package mvest.core.order.infrastructure;

import mvest.core.order.domain.Order;

public final class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getOrderId(),
                entity.getUserId(),
                entity.getStockCode(),
                entity.getOrderType(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static OrderEntity toEntity(Order domain) {
        return OrderEntity.builder()
                .id(domain.getId())
                .orderId(domain.getOrderId())
                .userId(domain.getUserId())
                .stockCode(domain.getStockCode())
                .orderType(domain.getOrderType())
                .price(domain.getPrice())
                .quantity(domain.getQuantity())
                .status(domain.getStatus())
                .build();
    }
}
