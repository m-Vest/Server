package mvest.core.stock.dto.response;

import mvest.core.stock.domain.StockDataStatus;
import mvest.core.stock.domain.StockPrice;

import java.math.BigDecimal;

public record StockTradingInfoDTO(
        String stockCode,
        String stockName,
        BigDecimal price,
        long change,
        double changeRate,
        StockDataStatus dataStatus,
        BigDecimal userBalance,
        int userStockQuantity
) {

    public static StockTradingInfoDTO from(
            StockPrice stockPrice,
            StockDataStatus status,
            BigDecimal balance,
            Integer stockQuantity
    ) {
        return new StockTradingInfoDTO(
                stockPrice.getStockCode(),
                stockPrice.getStockName(),
                stockPrice.getPrice(),
                stockPrice.getChange(),
                stockPrice.getChangeRate(),
                status,
                balance,
                stockQuantity == null ? 0 : stockQuantity
        );
    }
}
