package mvest.core.asset.infrastructure;


import mvest.core.asset.domain.UserStock;

public final class UserStockMapper {

    public static UserStock toDomain(UserStockEntity entity) {
        return new UserStock(
                entity.getId(),
                entity.getUserId(),
                entity.getStockCode(),
                entity.getQuantity()
        );
    }

    public static UserStockEntity toEntity(UserStock domain) {
        return UserStockEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .stockCode(domain.getStockCode())
                .quantity(domain.getQuantity())
                .build();
    }
}
