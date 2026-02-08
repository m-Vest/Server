package mvest.core.asset.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.payload.AssetAppliedEventPayload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssetCommandService {

    private final UserStockRepository userStockRepository;
    private final UserCashRepository userCashRepository;
    private final AssetTransactionRepository  assetTransactionRepository;

    public void updateAssetChange(AssetAppliedEventPayload payload) {
        //TODO
    }
}
