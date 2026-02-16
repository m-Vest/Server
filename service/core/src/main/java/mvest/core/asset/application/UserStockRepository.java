package mvest.core.asset.application;

import mvest.core.asset.domain.UserStock;

import java.util.List;

public interface UserStockRepository {
    void increase(Long userId, String stockCode, Integer quantity);
    void decrease(Long userId, String stockCode, Integer quantity);
    Integer findByUserIdAndStockCode(Long userId, String stockCode);
    List<UserStock> findAllByUserId(Long userId);
}
