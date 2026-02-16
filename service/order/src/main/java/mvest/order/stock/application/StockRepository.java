package mvest.order.stock.application;

import mvest.order.stock.domain.StockPrice;

import java.util.Optional;

public interface StockRepository {
    Optional<StockPrice> findByStockCode(String stockCode);
}
