package mvest.order.application;

import mvest.order.domain.Order;

public interface OrderRepository {
    void save(Order order);
}
