package mvest.common.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvest.common.event.EventPayload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderExecutedEventPayload implements EventPayload {
    private String orderId;
    private Long userId;
    private String stockCode;
    private OrderType orderType;
    private BigDecimal executedPrice;
    private Integer executedQuantity;
    private LocalDateTime occurredAt;
}
