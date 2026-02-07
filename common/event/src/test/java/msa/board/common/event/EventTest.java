package msa.board.common.event;

import msa.board.common.event.payload.OrderSubmittedEventPayload;
import msa.board.common.event.payload.OrderType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    void orderSubmittedEvent_serde() {
        // given
        LocalDateTime now = LocalDateTime.now();

        OrderSubmittedEventPayload payload =
                OrderSubmittedEventPayload.builder()
                        .orderId("order-123")
                        .userId(1L)
                        .stockCode("AAPL")
                        .orderType(OrderType.BUY)
                        .price(new BigDecimal("150.00"))
                        .quantity(10)
                        .occurredAt(now)
                        .build();

        Event<EventPayload> event = Event.of(
                1001L,
                EventType.ORDER_SUBMITTED,
                payload
        );

        String json = event.toJson();
        System.out.println("json = " + json);

        // when
        Event<EventPayload> result = Event.fromJson(json);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEventId()).isEqualTo(event.getEventId());
        assertThat(result.getType()).isEqualTo(event.getType());
        assertThat(result.getPayload()).isInstanceOf(OrderSubmittedEventPayload.class);

        OrderSubmittedEventPayload resultPayload =
                (OrderSubmittedEventPayload) result.getPayload();

        assertThat(resultPayload.getOrderId()).isEqualTo(payload.getOrderId());
        assertThat(resultPayload.getUserId()).isEqualTo(payload.getUserId());
        assertThat(resultPayload.getStockCode()).isEqualTo(payload.getStockCode());
        assertThat(resultPayload.getOrderType()).isEqualTo(payload.getOrderType());
        assertThat(resultPayload.getPrice()).isEqualByComparingTo(payload.getPrice());
        assertThat(resultPayload.getQuantity()).isEqualTo(payload.getQuantity());
        assertThat(resultPayload.getOccurredAt()).isEqualTo(payload.getOccurredAt());
    }
}
