package msa.board.common.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.board.common.event.EventPayload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetAppliedEventPayload implements EventPayload {

    private String orderId;
    private Long userId;
    private String stockCode;
    private OrderType orderType;

    private BigDecimal cashChange;
    private Integer stockQuantityChange;

    private LocalDateTime occurredAt;
}
