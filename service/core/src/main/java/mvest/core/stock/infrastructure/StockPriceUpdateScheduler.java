package mvest.core.stock.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.core.global.lock.DistributedLockManager;
import mvest.core.stock.application.StockPriceOpenApiClient;
import mvest.core.stock.domain.StockPrice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockPriceUpdateScheduler {

    private static final String LOCK_KEY = "lock:stock-price-update";

    private final DistributedLockManager lockManager;
    private final StockPriceOpenApiClient openApiClient;
    private final StockPriceRedisRepository redisRepository;

    @Scheduled(fixedRate = 60_000)
    public void updateStockPrices() {

        boolean locked = lockManager.tryLock(LOCK_KEY, 55_000);

        if (!locked) {
            log.debug("Another instance is running stock update job.");
            return;
        }

        try {
            List<StockPrice> prices = openApiClient.fetchStockPrices();
            redisRepository.save(prices);
        } finally {
            lockManager.releaseLock(LOCK_KEY);
        }
    }
}
