package mvest.core.asset.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssetDailySnapshotDTO(
        LocalDate snapshotDate,
        BigDecimal totalAsset,
        BigDecimal cashAmount,
        BigDecimal stockEvaluationAmount
) {
}
