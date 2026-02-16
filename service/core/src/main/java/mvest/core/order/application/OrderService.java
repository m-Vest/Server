package mvest.core.order.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.EventType;
import mvest.common.event.payload.OrderSubmittedEventPayload;
import mvest.common.event.payload.OrderType;
import mvest.common.outboxmessagerelay.OutboxEventPublisher;
import mvest.common.snowflake.Snowflake;
import mvest.core.asset.application.UserCashRepository;
import mvest.core.asset.application.UserStockRepository;
import mvest.core.global.code.OrderErrorCode;
import mvest.core.global.exception.BusinessException;
import mvest.core.order.domain.Order;
import mvest.core.order.domain.OrderStatus;
import mvest.core.order.dto.request.OrderCreateDTO;
import mvest.core.order.dto.response.OrderCreatedDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final Snowflake snowflake = new Snowflake();
    private final OutboxEventPublisher outboxEventPublisher;
    private final OrderRepository orderRepository;
    private final UserStockRepository userStockRepository;
    private final UserCashRepository userCashRepository;

    @Transactional
    public OrderCreatedDTO createOrder(OrderCreateDTO orderCreateDTO, Long userId) {

        validateOrder(orderCreateDTO, userId);

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

    private void validateOrder(OrderCreateDTO orderCreateDTO, Long userId) {
        if (orderCreateDTO.orderType().equals(OrderType.BUY)) {

            BigDecimal requiredAmount = orderCreateDTO.price().multiply(BigDecimal.valueOf(orderCreateDTO.quantity()));
            BigDecimal currentBalance = userCashRepository.findByUserId(userId).getBalance();

            if (currentBalance.compareTo(requiredAmount) < 0) {
                throw new BusinessException(OrderErrorCode.INSUFFICIENT_BALANCE);
            }

        } else if (orderCreateDTO.orderType().equals(OrderType.SELL)) {

            Integer currentQuantity = userStockRepository.findByUserIdAndStockCode(userId, orderCreateDTO.stockCode());

            if (currentQuantity < orderCreateDTO.quantity()) {
                throw new BusinessException(OrderErrorCode.INSUFFICIENT_STOCK);
            }
        }
    }
}
