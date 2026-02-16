package mvest.core.asset.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.payload.AssetChangeEventPayload;
import mvest.common.event.payload.OrderType;
import mvest.core.asset.domain.AssetTransaction;
import mvest.core.asset.domain.UserCash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetCommandService {

    private static final BigDecimal INITIAL_CASH = BigDecimal.valueOf(1_000_000);

    private final UserCashRepository userCashRepository;
    private final UserStockRepository userStockRepository;
    private final AssetTransactionRepository assetTransactionRepository;

    public void applyAssetChange(AssetChangeEventPayload payload) {

        if (payload.getOrderType() == OrderType.INITIAL_DEPOSIT) {
            // 중복 이벤트 방어 (멱등성)
            if (userCashRepository.existsByUserId(payload.getUserId())) {
                return;
            }

            UserCash userCash = UserCash.initial(payload.getUserId(), INITIAL_CASH);
            userCashRepository.save(userCash);

            AssetTransaction assetTransaction = AssetTransaction.initialDeposit(payload.getUserId(), INITIAL_CASH);
            assetTransactionRepository.save(assetTransaction);

            return;
        }

        // 중복 이벤트 방어 (멱등성)
        if (assetTransactionRepository.existsByOrderId(payload.getOrderId())) {
            return;
        }

        BigDecimal amount = payload.getPrice().multiply(BigDecimal.valueOf(payload.getQuantity()));

        if (payload.getOrderType() == OrderType.BUY) {
            userCashRepository.decrease(payload.getUserId(), amount);
            userStockRepository.increase(payload.getUserId(), payload.getStockCode(), payload.getQuantity());
        } else if (payload.getOrderType() == OrderType.SELL) {
            userCashRepository.increase(payload.getUserId(), amount);
            userStockRepository.decrease(payload.getUserId(), payload.getStockCode(), payload.getQuantity());
        }

        assetTransactionRepository.save(AssetTransaction.fromOrder(payload));
    }
}
