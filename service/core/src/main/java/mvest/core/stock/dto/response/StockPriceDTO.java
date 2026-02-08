package mvest.core.stock.dto.response;

import mvest.core.stock.domain.StockPrice;

public record StockPriceDTO(
        String stockCode,
        String stockName,
        long price,
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
