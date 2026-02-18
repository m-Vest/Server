package mvest.core.asset.infrastructure;

import mvest.core.asset.domain.AssetDailySnapshot;

public class AssetDailySnapshotMapper {

    public static AssetDailySnapshot toDomain(AssetDailySnapshotEntity entity) {
        return new AssetDailySnapshot(
                entity.getId(),
                entity.getUserId(),
                entity.getSnapshotDate(),
                entity.getTotalAsset(),
                entity.getCashAmount(),
                entity.getStockEvaluationAmount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static AssetDailySnapshotEntity toEntity(AssetDailySnapshot domain) {
        return new AssetDailySnapshotEntity(
                domain.getUserId(),
                domain.getSnapshotDate(),
                domain.getTotalAsset(),
                domain.getCashAmount(),
                domain.getStockEvaluationAmount()
        );
    }
}
