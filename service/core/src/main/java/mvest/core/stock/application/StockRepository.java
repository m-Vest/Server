package mvest.core.stock.application;

import mvest.core.stock.domain.StockPrice;

import java.util.List;

public interface StockRepository {
    List<StockPrice> findAll();
}
