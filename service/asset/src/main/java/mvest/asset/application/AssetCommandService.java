package mvest.asset.application;

import lombok.RequiredArgsConstructor;
import mvest.asset.domain.AssetTransaction;
import mvest.asset.global.exception.BusinessException;
import mvest.asset.global.exception.DomainException;
import mvest.common.event.EventType;
import mvest.common.event.payload.AssetChangeEventPayload;
import mvest.common.event.payload.OrderType;
import mvest.common.event.payload.UserRegisteredEventPayload;
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

        // 멱등성 방어
        if (assetTransactionRepository.existsByOrderId(payload.getOrderId())) {
            return;
        }

        BigDecimal amount =
                payload.getPrice().multiply(BigDecimal.valueOf(payload.getQuantity()));

        try {

            if (payload.getOrderType() == OrderType.BUY) {

                userCashRepository.decrease(payload.getUserId(), amount);
                userStockRepository.increase(
                        payload.getUserId(),
                        payload.getStockCode(),
                        payload.getQuantity()
                );

            } else if (payload.getOrderType() == OrderType.SELL) {

                userCashRepository.increase(payload.getUserId(), amount);
                userStockRepository.decrease(
                        payload.getUserId(),
                        payload.getStockCode(),
                        payload.getQuantity()
                );
            }

            assetTransactionRepository.save(
                    AssetTransaction.fromOrder(payload)
            );

            outboxEventPublisher.publish(
                    EventType.ASSET_APPLIED,
                    AssetChangeEventPayload.builder()
                            .orderId(payload.getOrderId())
                            .userId(payload.getUserId())
                            .stockCode(payload.getStockCode())
                            .orderType(payload.getOrderType())
                            .price(payload.getPrice())
                            .quantity(payload.getQuantity())
                            .occurredAt(payload.getOccurredAt())
                            .build()
            );

        } catch (DomainException e) {
            throw new BusinessException(e.getErrorCode());
        }
    }
}
