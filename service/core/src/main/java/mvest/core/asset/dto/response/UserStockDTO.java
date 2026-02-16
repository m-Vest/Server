package mvest.core.asset.dto.response;

public record UserStockDTO(
        String stockCode,
        int quantity
) {
}
