package mvest.core.order.dto.response;

import mvest.common.event.payload.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCreatedDTO(
        String orderId,
        Long userId,
        String stockCode,
        OrderType orderType,
        BigDecimal price,
        Integer quantity,
        LocalDateTime occurredAt
) {
}
