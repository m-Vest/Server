package mvest.common.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvest.common.event.EventPayload;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRejectedEventPayload implements EventPayload {
    private String orderId;
    private Long userId;
    private String reason;
    private LocalDateTime occurredAt;
}
