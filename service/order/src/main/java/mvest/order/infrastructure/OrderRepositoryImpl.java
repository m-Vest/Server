package mvest.order.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.order.application.OrderRepository;
import mvest.order.domain.Order;
import org.springframework.stereotype.Repository;

import static mvest.order.infrastructure.OrderMapper.toEntity;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public void save(Order order) {
        orderJpaRepository.save(toEntity(order));
    }
}
