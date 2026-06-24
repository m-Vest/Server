package mvest.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByOrderId(String orderId);
    long countByOrderId(String orderId);
}
