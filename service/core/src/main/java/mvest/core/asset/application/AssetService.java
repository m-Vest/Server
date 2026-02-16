package mvest.core.asset.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventPayload;
import mvest.core.asset.domain.UserCash;
import mvest.core.asset.dto.response.UserAssetDTO;
import mvest.core.asset.dto.response.UserAssetTransactionDTO;
import mvest.core.asset.dto.response.UserAssetTransactionItemDTO;
import mvest.core.asset.dto.response.UserStockDTO;
import org.springframework.stereotype.Service;
import mvest.core.asset.application.handler.EventHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final List<EventHandler> eventHandlers;
    private final UserCashRepository userCashRepository;
    private final UserStockRepository userStockRepository;
    private final AssetTransactionRepository assetTransactionRepository;

    public void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }

    public UserAssetDTO getUserAsset(Long userId) {

        UserCash userCash = userCashRepository.findByUserId(userId);

        List<UserStockDTO> stocks = userStockRepository
                .findAllByUserId(userId)
                .stream()
                .map(stock ->
                        new UserStockDTO(
                                stock.getStockCode(),
                                stock.getQuantity()
                        )
                )
                .toList();

        return new UserAssetDTO(
                userCash.getUserId(),
                userCash.getBalance(),
                userCash.getUpdatedAt(),
                stocks
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
