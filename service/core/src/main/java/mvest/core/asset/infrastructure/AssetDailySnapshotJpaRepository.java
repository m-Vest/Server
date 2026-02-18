package mvest.core.asset.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AssetDailySnapshotJpaRepository
        extends JpaRepository<AssetDailySnapshotEntity, Long> {

    Optional<AssetDailySnapshotEntity> findByUserIdAndSnapshotDate(
            Long userId,
            LocalDate snapshotDate
    );
}
