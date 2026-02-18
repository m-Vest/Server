package mvest.core.stock.dto.response;

import mvest.core.stock.domain.StockDataStatus;

import java.util.List;

public record StockPriceListDTO(
        List<StockPriceDTO> stockPriceDtoList,
        StockDataStatus dataStatus
) {
    public static StockPriceListDTO of(
            List<StockPriceDTO> stockPriceList,
            StockDataStatus status
    ) {
        return new StockPriceListDTO(stockPriceList, status);
    }
}
