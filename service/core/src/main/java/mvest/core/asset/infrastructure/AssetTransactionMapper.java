package mvest.core.asset.infrastructure;

import mvest.core.asset.domain.AssetTransaction;

public final class AssetTransactionMapper {

    public static AssetTransaction toDomain(AssetTransactionEntity entity) {
        return new AssetTransaction(
                entity.getId(),
                entity.getOrderId(),
                entity.getUserId(),
                entity.getStockCode(),
                entity.getTransactionType(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCashChange(),
                entity.getCreatedAt()
        );
    }

    public static AssetTransactionEntity toEntity(AssetTransaction domain) {
        return AssetTransactionEntity.builder()
                .id(domain.getId())
                .orderId(domain.getOrderId())
                .userId(domain.getUserId())
                .stockCode(domain.getStockCode())
                .transactionType(domain.getTransactionType())
                .price(domain.getPrice())
                .quantity(domain.getQuantity())
                .cashChange(domain.getCashChange())
                .build();
    }
}
