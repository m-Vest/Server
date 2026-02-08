package mvest.core.stock.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.core.stock.application.StockPriceOpenApiClient;
import mvest.core.stock.domain.StockPrice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockPriceUpdateScheduler {

    private final StockPriceOpenApiClient openApiClient;
    private final StockPriceRedisRepository redisRepository;

    @Scheduled(fixedRate = 60_000)
    public void updateStockPrices() {
        List<StockPrice> prices = openApiClient.fetchStockPrices();
        redisRepository.save(prices);
    }
}
