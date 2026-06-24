package mvest.order.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.order.application.OrderRepository;
import mvest.order.domain.Order;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public void save(Order order) {
        log.info("Saving order with createdAt {}", order.getCreatedAt());
        OrderEntity entity = OrderMapper.toEntity(order);
        orderJpaRepository.save(entity);
    }

    @Override
    public boolean existsByOrderId(String orderId) {
        return orderJpaRepository.existsByOrderId(orderId);
    }
}
