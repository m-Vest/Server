package mvest.core.asset.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockJpaRepository extends JpaRepository<UserStockEntity, Long> {
}
