package mvest.core.asset.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record UserAssetDTO(
        Long userId,
        BigDecimal balance,
        LocalDateTime updatedAt,
        List<UserStockDTO> stocks
) {
}
