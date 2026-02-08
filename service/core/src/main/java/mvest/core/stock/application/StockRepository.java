package mvest.core.stock.application;

import mvest.core.stock.domain.StockPrice;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    List<StockPrice> findAll();
    Optional<StockPrice> findByStockCode(String stockCode);
}
