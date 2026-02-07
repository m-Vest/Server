package msa.board.common.event.payload;

import msa.board.common.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmittedEventPayload implements EventPayload {
    private String orderId;
    private Long userId;
    private String stockCode;
    private OrderType orderType;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime occurredAt;
}
