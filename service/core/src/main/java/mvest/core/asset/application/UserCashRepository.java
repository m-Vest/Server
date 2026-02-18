package mvest.core.asset.application;

import mvest.core.asset.domain.UserCash;

import java.math.BigDecimal;
import java.util.List;

public interface UserCashRepository {
    void save(UserCash userCash);
    boolean existsByUserId(Long userId);
    void increase(Long userId, BigDecimal amount);
    void decrease(Long userId, BigDecimal amount);
    UserCash findByUserId(Long userId);
    List<Long> findAllUserIds();
}
