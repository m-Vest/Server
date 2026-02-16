package mvest.asset.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.asset.domain.UserCash;
import mvest.asset.application.UserCashRepository;
import mvest.asset.global.code.DomainErrorCode;
import mvest.asset.global.exception.DomainException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class UserCashRepositoryImpl implements UserCashRepository {

    private final UserCashJpaRepository userCashJpaRepository;

    @Override
    public void save(UserCash userCash) {
        UserCashEntity entity = UserCashMapper.toEntity(userCash);
        userCashJpaRepository.save(entity);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return userCashJpaRepository.existsById(userId);
    }

    @Override
    public void increase(Long userId, BigDecimal amount) {
        UserCashEntity entity = userCashJpaRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainErrorCode.ASSET_NOT_FOUND));

        entity.increase(amount);
    }

    @Override
    public void decrease(Long userId, BigDecimal amount) {
        UserCashEntity entity = userCashJpaRepository.findById(userId)
                .orElseThrow(() -> new DomainException(DomainErrorCode.ASSET_NOT_FOUND));

        entity.decrease(amount);
    }
}
