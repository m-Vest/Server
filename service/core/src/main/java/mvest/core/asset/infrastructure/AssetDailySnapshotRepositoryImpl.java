package mvest.core.asset.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.core.asset.application.AssetDailySnapshotRepository;
import mvest.core.asset.domain.AssetDailySnapshot;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AssetDailySnapshotRepositoryImpl
        implements AssetDailySnapshotRepository {

    private final AssetDailySnapshotJpaRepository jpaRepository;

    @Override
    public List<AssetDailySnapshot> findAllByUserId(Long userId) {
        return jpaRepository
                .findAllByUserIdOrderBySnapshotDateAsc(userId)
                .stream()
                .map(AssetDailySnapshotMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AssetDailySnapshot> findByUserIdAndDate(Long userId, LocalDate date) {
        return jpaRepository
                .findByUserIdAndSnapshotDate(userId, date)
                .map(AssetDailySnapshotMapper::toDomain);
    }

    @Override
    public void save(AssetDailySnapshot snapshot) {
        jpaRepository.save(AssetDailySnapshotMapper.toEntity(snapshot));
    }

    @Override
    public void update(AssetDailySnapshot snapshot) {
        AssetDailySnapshotEntity entity =
                jpaRepository.findByUserIdAndSnapshotDate(
                        snapshot.getUserId(),
                        snapshot.getSnapshotDate()
                ).orElseThrow();

        entity.update(
                snapshot.getTotalAsset(),
                snapshot.getCashAmount(),
                snapshot.getStockEvaluationAmount()
        );
    }
}
