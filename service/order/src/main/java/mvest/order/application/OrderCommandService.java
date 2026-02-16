package mvest.order.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.payload.OrderSubmittedEventPayload;
import mvest.common.event.payload.OrderType;
import mvest.order.domain.Order;
import mvest.order.domain.OrderStatus;
import mvest.order.global.code.StockErrorCode;
import mvest.order.global.exception.BusinessException;
import mvest.order.stock.application.StockRepository;
import mvest.order.stock.domain.StockPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandService {

    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;

    public void executeOrder(OrderSubmittedEventPayload payload) {

        StockPrice stockPrice = stockRepository.findByStockCode(payload.getStockCode())
                .orElseThrow(() -> new BusinessException(StockErrorCode.STOCK_NOT_FOUND));

        BigDecimal currentPrice = stockPrice.getPrice();
        BigDecimal orderPrice = payload.getPrice();

        OrderType orderType = payload.getOrderType();
        OrderStatus status;

        if (orderType == OrderType.BUY) {

            // 매수: 주문가 >= 현재가 → 즉시 체결
            if (orderPrice.compareTo(currentPrice) >= 0) {
                status = OrderStatus.EXECUTED;
            } else {
                status = OrderStatus.PENDING;
            }

        } else if (orderType == OrderType.SELL) {

            // 매도: 주문가 <= 현재가 → 즉시 체결
            if (orderPrice.compareTo(currentPrice) <= 0) {
                status = OrderStatus.EXECUTED;
            } else {
                status = OrderStatus.PENDING;
            }

        } else {
            status = OrderStatus.REJECTED;
        }

        Order order = Order.create(
                payload.getOrderId(),
                payload.getUserId(),
                payload.getStockCode(),
                orderType,
                orderPrice,
                payload.getQuantity(),
                status
        );

        orderRepository.save(order);
    }
}
