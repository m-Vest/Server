package mvest.core.stock.application;

import lombok.RequiredArgsConstructor;
import mvest.core.asset.application.UserCashRepository;
import mvest.core.asset.application.UserStockRepository;
import mvest.core.asset.domain.UserCash;
import mvest.core.asset.domain.UserStock;
import mvest.core.global.code.StockErrorCode;
import mvest.core.global.exception.BusinessException;
import mvest.core.stock.domain.StockDataStatus;
import mvest.core.stock.domain.StockPrice;
import mvest.core.stock.dto.response.StockPriceDTO;
import mvest.core.stock.dto.response.StockPriceListDTO;
import mvest.core.stock.dto.response.StockTradingInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final UserCashRepository userCashRepository;
    private final UserStockRepository userStockRepository;

    public StockPriceListDTO getAllStockPrices() {

        List<StockPrice> stockPriceList = stockRepository.findAll();

        if (stockPriceList.isEmpty()) {
            throw new BusinessException(StockErrorCode.STOCK_LIST_NOT_FOUND);
        }

        StockDataStatus status = stockRepository.getDataStatus();

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

        StockDataStatus status = stockRepository.getDataStatus();

        return StockPriceDTO.from(stockPrice, status);
    }

    public StockTradingInfoDTO getStockTradingInfo(Long userId, String stockCode) {

        StockPrice stockPrice = stockRepository.findByStockCode(stockCode)
                .orElseThrow(() ->
                        new BusinessException(StockErrorCode.STOCK_NOT_FOUND)
                );

        StockDataStatus status = stockRepository.getDataStatus();

        UserCash userCash = userCashRepository.findByUserId(userId);
        Integer stockQuantity = userStockRepository.findByUserIdAndStockCode(userId, stockCode);

        return StockTradingInfoDTO.from(stockPrice, status, userCash.getBalance(), stockQuantity);
    }
}
