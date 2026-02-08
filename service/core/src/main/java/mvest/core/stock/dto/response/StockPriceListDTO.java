package mvest.core.stock.dto.response;

import java.util.List;

public record StockPriceListDTO(List<StockPriceDTO> stockPriceDtoList) {

    public static StockPriceListDTO of(List<StockPriceDTO> stockPriceList) {
        return new StockPriceListDTO(stockPriceList);
    }
}
