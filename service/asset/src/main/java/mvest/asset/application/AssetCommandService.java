package mvest.asset.application;

import lombok.RequiredArgsConstructor;
import mvest.asset.domain.AssetTransaction;
import mvest.common.event.payload.AssetChangeEventPayload;
import mvest.common.event.payload.OrderType;
import mvest.common.outboxmessagerelay.OutboxEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetCommandService {

    private final UserCashRepository userCashRepository;
    private final UserStockRepository userStockRepository;
    private final AssetTransactionRepository assetTransactionRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    public void applyAssetChange(AssetChangeEventPayload payload) {

        // 중복 이벤트 방어 (멱등성)
        if (assetTransactionRepository.existsByOrderId(payload.getOrderId())) {
            return;
        }

        BigDecimal amount = payload.getPrice().multiply(BigDecimal.valueOf(payload.getQuantity()));

        if (payload.getOrderType() == OrderType.BUY) {
            userCashRepository.decrease(payload.getUserId(), amount);
            userStockRepository.increase(payload.getUserId(), payload.getStockCode(), payload.getQuantity());
        } else {
            userCashRepository.increase(payload.getUserId(), amount);
            userStockRepository.decrease(payload.getUserId(), payload.getStockCode(), payload.getQuantity());
        }

        assetTransactionRepository.save(AssetTransaction.fromOrder(payload));
    }
}
