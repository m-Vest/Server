package mvest.core.asset.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.core.asset.domain.UserStock;
import mvest.core.asset.infrastructure.AssetDailySnapshotEntity;
import mvest.core.asset.infrastructure.AssetDailySnapshotJpaRepository;
import mvest.core.stock.application.StockRepository;
import mvest.core.stock.domain.StockPrice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssetSnapshotScheduler {

    private final AssetDailySnapshotJpaRepository snapshotRepository;
    private final UserCashRepository userCashRepository;
    private final UserStockRepository userStockRepository;
    private final StockRepository stockRepository;

    /**
     * 매 1시간마다 실행
     */
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void recordDailySnapshot() {

        log.info("[AssetSnapshotScheduler] snapshot job started");

        LocalDate today = LocalDate.now();

        // 현재가 전체 조회 (Redis 1회 파싱)
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

                // 현금 조회
                BigDecimal cash = userCashRepository
                        .findByUserId(userId)
                        .getBalance();

                // 보유 주식 평가액 계산 (Map 전달)
                BigDecimal stockEvaluation = calculateStockEvaluation(userId, priceMap);

                BigDecimal totalAsset = cash.add(stockEvaluation);

                // snapshot 생성
                snapshotRepository
                        .findByUserIdAndSnapshotDate(userId, today)
                        .ifPresentOrElse(
                                entity -> {
                                    entity.update(totalAsset, cash, stockEvaluation);
                                    log.debug("Updated snapshot: userId={}, date={}", userId, today);
                                },
                                () -> {
                                    AssetDailySnapshotEntity newEntity =
                                            new AssetDailySnapshotEntity(
                                                    userId,
                                                    today,
                                                    totalAsset,
                                                    cash,
                                                    stockEvaluation
                                            );
                                    snapshotRepository.save(newEntity);
                                    log.debug("Inserted snapshot: userId={}, date={}", userId, today);
                                }
                        );

            } catch (Exception e) {
                log.error("Snapshot failed for userId={}", userId, e);
            }
        }

        log.info("[AssetSnapshotScheduler] snapshot job finished");
    }

    private BigDecimal calculateStockEvaluation(
            Long userId,
            Map<String, BigDecimal> priceMap
    ) {
        List<UserStock> userStocks = userStockRepository.findAllByUserId(userId);

        if (userStocks.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (UserStock userStock : userStocks) {

            BigDecimal currentPrice = priceMap.get(userStock.getStockCode());

            if (currentPrice == null) continue;

            total = total.add(
                    currentPrice.multiply(BigDecimal.valueOf(userStock.getQuantity()))
            );
        }

        return total;
    }
}
