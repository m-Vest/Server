package mvest.asset.application;

import mvest.asset.domain.AssetTransaction;

public interface AssetTransactionRepository {
    void save(AssetTransaction transaction);
    boolean existsByOrderId(String orderId);
}
