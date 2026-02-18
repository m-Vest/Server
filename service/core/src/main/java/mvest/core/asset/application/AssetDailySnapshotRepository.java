package mvest.core.asset.application;

import mvest.core.asset.domain.AssetDailySnapshot;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssetDailySnapshotRepository {

    List<AssetDailySnapshot> findAllByUserId(Long userId);

    Optional<AssetDailySnapshot> findByUserIdAndDate(Long userId, LocalDate date);

    void save(AssetDailySnapshot snapshot);

    void update(AssetDailySnapshot snapshot);
}
