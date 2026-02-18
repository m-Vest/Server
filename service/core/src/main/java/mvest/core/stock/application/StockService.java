package mvest.core.stock.application;

import lombok.RequiredArgsConstructor;
import mvest.core.global.code.StockErrorCode;
import mvest.core.global.exception.BusinessException;
import mvest.core.stock.domain.StockDataStatus;
import mvest.core.stock.domain.StockPrice;
import mvest.core.stock.dto.response.StockPriceDTO;
import mvest.core.stock.dto.response.StockPriceListDTO;
import mvest.core.stock.infrastructure.StockRepositoryImpl;
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

        StockDataStatus status =
                ((StockRepositoryImpl) stockRepository).getDataStatus();

        return StockPriceListDTO.of(
                stockPriceList.stream()
                        .map(stock -> StockPriceDTO.from(stock, status))
                        .toList(),
                status
        );
    }

    public StockPriceDTO getStockPrice(String stockCode) {

        StockPrice stockPrice = stockRepository.findByStockCode(stockCode)
                .orElseThrow(() ->
                        new BusinessException(StockErrorCode.STOCK_NOT_FOUND)
                );

        StockDataStatus status =
                ((StockRepositoryImpl) stockRepository).getDataStatus();

        return StockPriceDTO.from(stockPrice, status);
    }
}
