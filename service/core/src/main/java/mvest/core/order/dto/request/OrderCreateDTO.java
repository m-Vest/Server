package mvest.core.order.dto.request;

import jakarta.validation.constraints.NotNull;
import mvest.common.event.payload.OrderType;

import java.math.BigDecimal;

public record OrderCreateDTO(
        @NotNull(message = "종목 코드은 필수 값입니다.") String stockCode,
        @NotNull(message = "주문 유형은 필수 값입니다.") OrderType orderType,
        @NotNull(message = "주문 가격은 필수 값입니다.") BigDecimal price,
        @NotNull(message = "주문 수량은 필수 값입니다.") Integer quantity
) {
}


