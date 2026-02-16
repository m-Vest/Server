package mvest.asset.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStockJpaRepository extends JpaRepository<UserStockEntity, Long> {

    Optional<UserStockEntity> findByUserIdAndStockCode(Long userId, String stockCode);

    void deleteByUserIdAndStockCode(Long userId, String stockCode);
}
