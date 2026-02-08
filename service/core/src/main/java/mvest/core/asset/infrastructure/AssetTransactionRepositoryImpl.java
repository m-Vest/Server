package mvest.core.asset.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.core.asset.application.AssetTransactionRepository;
import mvest.core.asset.domain.AssetTransaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AssetTransactionRepositoryImpl implements AssetTransactionRepository {

    private final AssetTransactionJpaRepository assetTransactionJpaRepository;

    @Override
    public void save(AssetTransaction transaction) {
        AssetTransactionEntity entity =
                AssetTransactionMapper.toEntity(transaction);
        assetTransactionJpaRepository.save(entity);
    }

    @Override
    public boolean existsByOrderId(String orderId) {
        return assetTransactionJpaRepository.existsByOrderId(orderId);
    }
}
