package mvest.asset.application;

import mvest.asset.domain.UserCash;

import java.math.BigDecimal;

public interface UserCashRepository {
    void save(UserCash userCash);
    boolean existsByUserId(Long userId);
    void increase(Long userId, BigDecimal amount);
    void decrease(Long userId, BigDecimal amount);
}
