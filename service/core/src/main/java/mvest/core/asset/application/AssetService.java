package mvest.core.asset.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventPayload;
import mvest.core.asset.domain.AssetTransaction;
import mvest.core.asset.domain.UserCash;
import mvest.core.asset.domain.UserStock;
import mvest.core.asset.dto.response.UserAssetDTO;
import mvest.core.asset.dto.response.UserAssetTransactionDTO;
import mvest.core.asset.dto.response.UserAssetTransactionItemDTO;
import mvest.core.asset.dto.response.UserStockDTO;
import mvest.core.stock.application.StockService;
import org.springframework.stereotype.Service;
import mvest.core.asset.application.handler.EventHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final List<EventHandler> eventHandlers;
    private final UserCashRepository userCashRepository;
    private final UserStockRepository userStockRepository;
    private final AssetTransactionRepository assetTransactionRepository;
    private final StockService stockService;

    public void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }

    public UserAssetDTO getUserAsset(Long userId) {

        UserCash userCash = userCashRepository.findByUserId(userId);
        List<UserStock> stocks = userStockRepository.findAllByUserId(userId);
        List<AssetTransaction> transactions =
                assetTransactionRepository.findAllByUserId(userId);

        List<UserStockDTO> detailList = stocks.stream()
                .map(stock -> {

                    String stockCode = stock.getStockCode();
                    int currentQuantity = stock.getQuantity();

                    // 현재가 조회
                    BigDecimal currentPrice =
                            stockService.getStockPrice(stockCode).price();

                    BigDecimal currentTotal =
                            currentPrice.multiply(BigDecimal.valueOf(currentQuantity));

                    // 해당 종목 거래만 추출 (INITIAL_DEPOSIT 제외)
                    List<AssetTransaction> stockTransactions = transactions.stream()
                            .filter(tx ->
                                    tx.getStockCode() != null
                                            && stockCode.equals(tx.getStockCode()))
                            .toList();

                    // 총 매수 수량
                    int totalBuyQuantity = stockTransactions.stream()
                            .filter(tx -> "BUY".equals(tx.getTransactionType()))
                            .mapToInt(AssetTransaction::getQuantity)
                            .sum();

                    // 총 매수 금액
                    BigDecimal totalBuyAmount = stockTransactions.stream()
                            .filter(tx -> "BUY".equals(tx.getTransactionType()))
                            .map(tx ->
                                    tx.getPrice().multiply(
                                            BigDecimal.valueOf(tx.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // 가중 평균 매입가
                    BigDecimal averageBuyPrice =
                            totalBuyQuantity == 0
                                    ? BigDecimal.ZERO
                                    : totalBuyAmount.divide(
                                    BigDecimal.valueOf(totalBuyQuantity),
                                    4,
                                    RoundingMode.HALF_UP
                            );

                    // 현재 보유 수량 기준 투자 원금
                    BigDecimal invested =
                            averageBuyPrice.multiply(BigDecimal.valueOf(currentQuantity));

                    BigDecimal profit = currentTotal.subtract(invested);

                    BigDecimal profitRate =
                            invested.compareTo(BigDecimal.ZERO) == 0
                                    ? BigDecimal.ZERO
                                    : profit.divide(invested, 4, RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(100));

                    return new UserStockDTO(
                            stockCode,
                            currentTotal,
                            invested,
                            currentPrice,
                            currentQuantity,
                            profit,
                            profitRate
                    );
                })
                .toList();

        // 전체 집계
        BigDecimal totalStockEvaluation = detailList.stream()
                .map(UserStockDTO::currentTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalInvested = detailList.stream()
                .map(UserStockDTO::investedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQuantity = detailList.stream()
                .mapToInt(UserStockDTO::quantity)
                .sum();

        BigDecimal totalProfit = totalStockEvaluation.subtract(totalInvested);

        BigDecimal totalProfitRate =
                totalInvested.compareTo(BigDecimal.ZERO) == 0
                        ? BigDecimal.ZERO
                        : totalProfit.divide(totalInvested, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));

        return new UserAssetDTO(
                userId,
                userCash.getBalance(),
                totalStockEvaluation,
                totalProfit,
                totalProfitRate,
                totalQuantity,
                detailList
        );
    }

    public UserAssetTransactionDTO getUserAssetTransaction(Long userId) {

        List<UserAssetTransactionItemDTO> transactions =
                assetTransactionRepository.findAllByUserId(userId)
                        .stream()
                        .map(tx ->
                                new UserAssetTransactionItemDTO(
                                        tx.getOrderId(),
                                        tx.getStockCode(),
                                        tx.getTransactionType(),
                                        tx.getPrice(),
                                        tx.getQuantity(),
                                        tx.getCashChange(),
                                        tx.getCreatedAt()
                                )
                        )
                        .toList();

        return new UserAssetTransactionDTO(
                userId,
                transactions
        );
    }
}
