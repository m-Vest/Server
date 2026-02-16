package mvest.core.order.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.EventType;
import mvest.common.event.payload.OrderSubmittedEventPayload;
import mvest.common.outboxmessagerelay.OutboxEventPublisher;
import mvest.common.snowflake.Snowflake;
import mvest.core.order.domain.Order;
import mvest.core.order.domain.OrderStatus;
import mvest.core.order.dto.request.OrderCreateDTO;
import mvest.core.order.dto.response.OrderCreatedDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final Snowflake snowflake = new Snowflake();
    private final OutboxEventPublisher outboxEventPublisher;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderCreatedDTO createOrder(OrderCreateDTO orderCreateDTO, Long userId) {

        String orderId = String.valueOf(snowflake.nextId());

        Order order = Order.create(
                orderId,
                userId,
                orderCreateDTO.stockCode(),
                orderCreateDTO.orderType(),
                orderCreateDTO.price(),
                orderCreateDTO.quantity(),
                OrderStatus.PENDING
        );

        Order savedOrder = orderRepository.save(order);

        OrderSubmittedEventPayload payload =
                OrderSubmittedEventPayload.builder()
                        .orderId(orderId)
                        .userId(userId)
                        .stockCode(savedOrder.getStockCode())
                        .orderType(savedOrder.getOrderType())
                        .price(savedOrder.getPrice())
                        .quantity(savedOrder.getQuantity())
                        .occurredAt(savedOrder.getCreatedAt())
                        .build();

        outboxEventPublisher.publish(
                EventType.ORDER_SUBMITTED,
                payload
        );

        return new OrderCreatedDTO(
                savedOrder.getOrderId(),
                savedOrder.getUserId(),
                savedOrder.getStockCode(),
                savedOrder.getOrderType(),
                savedOrder.getPrice(),
                savedOrder.getQuantity(),
                savedOrder.getCreatedAt()
        );
    }
}
