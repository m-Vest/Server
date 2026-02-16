package mvest.core.asset.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserAssetTransactionItemDTO(
        String orderId,
        String stockCode,
        String transactionType,
        BigDecimal price,
        int quantity,
        BigDecimal cashChange,
        LocalDateTime createdAt
) {
}
