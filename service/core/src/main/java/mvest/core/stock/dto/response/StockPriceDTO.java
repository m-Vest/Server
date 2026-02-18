package mvest.core.stock.dto.response;

import mvest.core.stock.domain.StockDataStatus;
import mvest.core.stock.domain.StockPrice;

import java.math.BigDecimal;

public record StockPriceDTO(
        String stockCode,
        String stockName,
        BigDecimal price,
        long change,
        double changeRate,
        StockDataStatus dataStatus   // 🔥 추가
) {

    public static StockPriceDTO from(
            StockPrice stockPrice,
            StockDataStatus status
    ) {
        return new StockPriceDTO(
                stockPrice.getStockCode(),
                stockPrice.getStockName(),
                stockPrice.getPrice(),
                stockPrice.getChange(),
                stockPrice.getChangeRate(),
                status
        );
    }
}
