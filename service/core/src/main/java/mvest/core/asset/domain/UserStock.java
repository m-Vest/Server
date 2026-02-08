package mvest.core.asset.domain;

public class UserStock {

    private final Long id;
    private final Long userId;
    private final String stockCode;
    private final int quantity;

    public UserStock(Long id,
                     Long userId,
                     String stockCode,
                     int quantity) {
        this.id = id;
        this.userId = userId;
        this.stockCode = stockCode;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public int getQuantity() {
        return quantity;
    }
}
