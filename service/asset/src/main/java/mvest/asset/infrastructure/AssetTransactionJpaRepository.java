package mvest.asset.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetTransactionJpaRepository extends JpaRepository<AssetTransactionEntity, Long> {
    boolean existsByOrderId(String orderId);
}
