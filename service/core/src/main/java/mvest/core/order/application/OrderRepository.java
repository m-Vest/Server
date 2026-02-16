package mvest.core.order.application;

import mvest.core.order.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
