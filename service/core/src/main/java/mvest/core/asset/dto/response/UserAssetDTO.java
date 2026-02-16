package mvest.core.asset.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record UserAssetDTO(
        Long userId,
        BigDecimal balance,

        BigDecimal totalStockEvaluation,
        BigDecimal totalProfitAmount,
        BigDecimal totalProfitRate,
        Integer totalStockQuantity,

        List<UserStockDTO> stocks
) {
}
