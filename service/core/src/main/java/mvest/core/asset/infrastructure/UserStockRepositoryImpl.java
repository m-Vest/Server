package mvest.core.asset.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.core.asset.application.UserStockRepository;
import mvest.core.global.code.DomainErrorCode;
import mvest.core.global.exception.DomainException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserStockRepositoryImpl implements UserStockRepository {

    private final UserStockJpaRepository userStockJpaRepository;

    @Override
    public void increase(Long userId, String stockCode, Integer quantity) {

        UserStockEntity entity = userStockJpaRepository
                .findByUserIdAndStockCode(userId, stockCode)
                .orElse(null);

        if (entity == null) {
            userStockJpaRepository.save(
                    UserStockEntity.builder()
                            .userId(userId)
                            .stockCode(stockCode)
                            .quantity(quantity)
                            .build()
            );
        } else {
            entity.increase(quantity);
        }
    }

    @Override
    public void decrease(Long userId, String stockCode, Integer quantity) {

        UserStockEntity entity = userStockJpaRepository
                .findByUserIdAndStockCode(userId, stockCode)
                .orElseThrow(() -> new DomainException(DomainErrorCode.ASSET_NOT_FOUND));

        entity.decrease(quantity);

        if (entity.getQuantity() == 0) {
            userStockJpaRepository.delete(entity);
        }
    }

    @Override
    public Integer findByUserIdAndStockCode(Long userId, String stockCode) {
        return userStockJpaRepository
                .findByUserIdAndStockCode(userId, stockCode)
                .map(UserStockEntity::getQuantity)
                .orElse(0);
    }
}
