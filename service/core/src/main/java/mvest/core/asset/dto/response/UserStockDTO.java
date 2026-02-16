package mvest.core.asset.dto.response;

import java.math.BigDecimal;

public record UserStockDTO(
        String stockCode,

        BigDecimal currentTotalAmount,
        BigDecimal investedAmount,
        BigDecimal currentPrice,

        Integer quantity,

        BigDecimal profitAmount,
        BigDecimal profitRate
) {
}
