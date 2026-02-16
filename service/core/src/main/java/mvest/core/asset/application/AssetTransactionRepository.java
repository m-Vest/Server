package mvest.core.asset.application;

import mvest.core.asset.domain.AssetTransaction;

import java.util.List;

public interface AssetTransactionRepository {
    void save(AssetTransaction transaction);
    boolean existsByOrderId(String orderId);
    List<AssetTransaction> findAllByUserId(Long userId);
}
