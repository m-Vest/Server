package mvest.core.stock.application;

import lombok.RequiredArgsConstructor;
import mvest.core.global.code.StockErrorCode;
import mvest.core.global.exception.BusinessException;
import mvest.core.stock.domain.StockPrice;
import mvest.core.stock.dto.response.StockPriceDTO;
import mvest.core.stock.dto.response.StockPriceListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public StockPriceListDTO getAllStockPrices() {

        List<StockPrice> stockPriceList = stockRepository.findAll();

        if (stockPriceList.isEmpty()) {
            throw new BusinessException(StockErrorCode.STOCK_LIST_NOT_FOUND);
        }

        return StockPriceListDTO.of(
                stockPriceList.stream()
                        .map(StockPriceDTO::from)
                        .toList()
        );
    }

    public StockPriceDTO getStockPrice(String stockCode) {

        StockPrice stockPrice = stockRepository.findByStockCode(stockCode)
                .orElseThrow(() ->
                        new BusinessException(StockErrorCode.STOCK_NOT_FOUND)
                );

        return StockPriceDTO.from(stockPrice);
    }
}
