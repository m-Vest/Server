package mvest.core.asset.application;

import mvest.core.asset.domain.AssetTransaction;

public interface AssetTransactionRepository {
    void save(AssetTransaction transaction);
    boolean existsByOrderId(String orderId);
}
