package mvest.core.asset.infrastructure;

import mvest.core.asset.domain.AssetChangeHistory;

public final class AssetChangeHistoryMapper {

    public static AssetChangeHistory toDomain(AssetChangeHistoryEntity entity) {
        return new AssetChangeHistory(
                entity.getId(),
                entity.getOrderId(),
                entity.getUserId(),
                entity.getStockCode(),
                entity.getChangeType(),
                entity.getStockChange(),
                entity.getCashChange(),
                entity.getCreatedAt()
        );
    }

    public static AssetChangeHistoryEntity toEntity(AssetChangeHistory domain) {
        return AssetChangeHistoryEntity.builder()
                .id(domain.getId())
                .orderId(domain.getOrderId())
                .userId(domain.getUserId())
                .stockCode(domain.getStockCode())
                .changeType(domain.getChangeType())
                .stockChange(domain.getStockChange())
                .cashChange(domain.getCashChange())
                .build();
    }
}
