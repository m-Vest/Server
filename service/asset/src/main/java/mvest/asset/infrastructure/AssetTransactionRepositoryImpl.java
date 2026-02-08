package mvest.asset.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.asset.domain.AssetTransaction;
import mvest.asset.application.AssetTransactionRepository;
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
