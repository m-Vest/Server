package mvest.asset.application;

public interface UserStockRepository {
    void increase(Long userId, String stockCode, Integer quantity);
    void decrease(Long userId, String stockCode, Integer quantity);
}
