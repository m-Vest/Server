package mvest.core.order.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.core.order.application.OrderRepository;
import mvest.core.order.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity savedEntity = orderJpaRepository.save(entity);
        return OrderMapper.toDomain(savedEntity);
    }
}
