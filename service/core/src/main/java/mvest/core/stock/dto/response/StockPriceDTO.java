package mvest.core.stock.dto.response;

import mvest.core.stock.domain.StockPrice;

import java.math.BigDecimal;

public record StockPriceDTO(
        String stockCode,
        String stockName,
        BigDecimal price,
        long change,
        double changeRate
) {

    public static StockPriceDTO from(StockPrice stockPrice) {
        return new StockPriceDTO(
                stockPrice.getStockCode(),
                stockPrice.getStockName(),
                stockPrice.getPrice(),
                stockPrice.getChange(),
                stockPrice.getChangeRate()
        );
    }
}
