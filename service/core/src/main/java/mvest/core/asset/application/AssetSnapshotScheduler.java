package mvest.core.asset.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.core.asset.domain.AssetDailySnapshot;
import mvest.core.asset.domain.UserStock;
import mvest.core.global.lock.DistributedLockManager;
import mvest.core.stock.application.StockRepository;
import mvest.core.stock.domain.StockPrice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssetSnapshotScheduler {

    private static final String LOCK_KEY = "lock:asset-snapshot";
    private static final long LOCK_TTL_MILLIS = 55_000;

    private final DistributedLockManager lockManager;
    private final AssetDailySnapshotRepository snapshotRepository;
    private final UserCashRepository userCashRepository;
    private final UserStockRepository userStockRepository;
    private final StockRepository stockRepository;

    /**
     * 매 1분마다 실행
     */
    @Scheduled(cron = "0 * * * * *")
    public void recordDailySnapshot() {

        // 분산 락 획득 시도
        boolean locked = lockManager.tryLock(LOCK_KEY, LOCK_TTL_MILLIS);

        if (!locked) {
            log.debug("[AssetSnapshotScheduler] another instance is running.");
            return;
        }

        try {

            log.info("[AssetSnapshotScheduler] snapshot job started");

            LocalDate today = LocalDate.now();

            // 현재가 전체 조회 (Redis 1회 조회)
            List<StockPrice> stockPrices = stockRepository.findAll();

            Map<String, BigDecimal> priceMap = stockPrices.stream()
                    .collect(Collectors.toMap(
                            StockPrice::getStockCode,
                            StockPrice::getPrice
                    ));

            // 전체 유저 조회
            List<Long> userIds = userCashRepository.findAllUserIds();

            for (Long userId : userIds) {

                try {

                    BigDecimal cash =
                            userCashRepository.findByUserId(userId).getBalance();

                    BigDecimal stockEvaluation =
                            calculateStockEvaluation(userId, priceMap);

                    BigDecimal totalAsset = cash.add(stockEvaluation);

                    snapshotRepository
                            .findByUserIdAndDate(userId, today)
                            .ifPresentOrElse(
                                    existing -> {

                                        AssetDailySnapshot updated =
                                                existing.update(
                                                        totalAsset,
                                                        cash,
                                                        stockEvaluation
                                                );

                                        snapshotRepository.update(updated);

                                        log.debug("Updated snapshot: userId={}, date={}", userId, today);
                                    },
                                    () -> {

                                        AssetDailySnapshot snapshot =
                                                new AssetDailySnapshot(
                                                        null,
                                                        userId,
                                                        today,
                                                        totalAsset,
                                                        cash,
                                                        stockEvaluation,
                                                        LocalDateTime.now(),
                                                        LocalDateTime.now()
                                                );

                                        snapshotRepository.save(snapshot);

                                        log.debug("Inserted snapshot: userId={}, date={}", userId, today);
                                    }
                            );

                } catch (Exception e) {
                    log.error("Snapshot failed for userId={}", userId, e);
                }
            }

            log.info("[AssetSnapshotScheduler] snapshot job finished");

        } finally {
            lockManager.releaseLock(LOCK_KEY);
        }
    }

    private BigDecimal calculateStockEvaluation(
            Long userId,
            Map<String, BigDecimal> priceMap
    ) {
        List<UserStock> userStocks =
                userStockRepository.findAllByUserId(userId);

        BigDecimal total = BigDecimal.ZERO;

        for (UserStock userStock : userStocks) {

            BigDecimal price = priceMap.get(userStock.getStockCode());
            if (price == null) continue;

            total = total.add(
                    price.multiply(BigDecimal.valueOf(userStock.getQuantity()))
            );
        }

        return total;
    }
}
