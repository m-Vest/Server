package mvest.order.stock.application;

import lombok.RequiredArgsConstructor;
import mvest.order.global.code.StockErrorCode;
import mvest.order.global.exception.BusinessException;
import mvest.order.stock.domain.StockPrice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public StockPrice getStockPrice(String stockCode) {

        StockPrice stockPrice = stockRepository.findByStockCode(stockCode)
                .orElseThrow(() ->
                        new BusinessException(StockErrorCode.STOCK_NOT_FOUND)
                );

        return stockPrice;
    }
}
