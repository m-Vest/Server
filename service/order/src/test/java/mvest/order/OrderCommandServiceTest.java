package mvest.order;

import mvest.common.event.payload.OrderSubmittedEventPayload;
import mvest.common.event.payload.OrderType;
import mvest.order.application.OrderCommandService;
import mvest.order.application.OrderRepository;
import mvest.order.domain.Order;
import mvest.order.domain.OrderStatus;
import mvest.order.stock.application.StockRepository;
import mvest.order.stock.domain.StockPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderCommandService orderCommandService;

    @Test
    @DisplayName("BUY 주문가 >= 현재가 → 즉시 체결")
    void buyOrder_executed() {
        // given
        String stockCode = "005930";
        BigDecimal currentPrice = new BigDecimal("100000");
        BigDecimal orderPrice = new BigDecimal("100000");

        when(stockRepository.findByStockCode(stockCode))
                .thenReturn(Optional.of(new StockPrice(stockCode, currentPrice)));

        OrderSubmittedEventPayload payload =
                OrderSubmittedEventPayload.builder()
                        .orderId("order-1")
                        .userId(1L)
                        .stockCode(stockCode)
                        .orderType(OrderType.BUY)
                        .price(orderPrice)
                        .quantity(10)
                        .occurredAt(LocalDateTime.now())
                        .build();

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        // when
        orderCommandService.executeOrder(payload);

        // then
        verify(orderRepository).save(captor.capture());

        Order savedOrder = captor.getValue();

        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.EXECUTED);
        assertThat(savedOrder.getOrderType()).isEqualTo(OrderType.BUY);
    }

    @Test
    @DisplayName("BUY 주문가 < 현재가 → 대기")
    void buyOrder_pending() {
        // given
        String stockCode = "005930";
        BigDecimal currentPrice = new BigDecimal("100000");
        BigDecimal orderPrice = new BigDecimal("90000");

        when(stockRepository.findByStockCode(stockCode))
                .thenReturn(Optional.of(new StockPrice(stockCode, currentPrice)));

        OrderSubmittedEventPayload payload =
                OrderSubmittedEventPayload.builder()
                        .orderId("order-2")
                        .userId(1L)
                        .stockCode(stockCode)
                        .orderType(OrderType.BUY)
                        .price(orderPrice)
                        .quantity(10)
                        .occurredAt(LocalDateTime.now())
                        .build();

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        // when
        orderCommandService.executeOrder(payload);

        // then
        verify(orderRepository).save(captor.capture());

        Order savedOrder = captor.getValue();

        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    @DisplayName("SELL 주문가 <= 현재가 → 즉시 체결")
    void sellOrder_executed() {
        // given
        String stockCode = "005930";
        BigDecimal currentPrice = new BigDecimal("100000");
        BigDecimal orderPrice = new BigDecimal("90000");

        when(stockRepository.findByStockCode(stockCode))
                .thenReturn(Optional.of(new StockPrice(stockCode, currentPrice)));

        OrderSubmittedEventPayload payload =
                OrderSubmittedEventPayload.builder()
                        .orderId("order-3")
                        .userId(1L)
                        .stockCode(stockCode)
                        .orderType(OrderType.SELL)
                        .price(orderPrice)
                        .quantity(10)
                        .occurredAt(LocalDateTime.now())
                        .build();

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        // when
        orderCommandService.executeOrder(payload);

        // then
        verify(orderRepository).save(captor.capture());

        Order savedOrder = captor.getValue();

        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.EXECUTED);
        assertThat(savedOrder.getOrderType()).isEqualTo(OrderType.SELL);
    }

    @Test
    @DisplayName("SELL 주문가 > 현재가 → 대기")
    void sellOrder_pending() {
        // given
        String stockCode = "005930";
        BigDecimal currentPrice = new BigDecimal("100000");
        BigDecimal orderPrice = new BigDecimal("120000");

        when(stockRepository.findByStockCode(stockCode))
                .thenReturn(Optional.of(new StockPrice(stockCode, currentPrice)));

        OrderSubmittedEventPayload payload =
                OrderSubmittedEventPayload.builder()
                        .orderId("order-4")
                        .userId(1L)
                        .stockCode(stockCode)
                        .orderType(OrderType.SELL)
                        .price(orderPrice)
                        .quantity(10)
                        .occurredAt(LocalDateTime.now())
                        .build();

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        // when
        orderCommandService.executeOrder(payload);

        // then
        verify(orderRepository).save(captor.capture());

        Order savedOrder = captor.getValue();

        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.PENDING);
    }
}

