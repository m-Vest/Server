package mvest.core.asset.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetTransactionJpaRepository extends JpaRepository<AssetTransactionEntity, Long> {
    boolean existsByOrderId(String orderId);
    List<AssetTransactionEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
