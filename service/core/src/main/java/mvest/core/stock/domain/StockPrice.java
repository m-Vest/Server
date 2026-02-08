package mvest.core.stock.domain;

public class StockPrice {

    private String stockCode;
    private String stockName;
    private long price;
    private long change;
    private double changeRate;

    public StockPrice() {
    }

    // 전체 필드 생성자
    public StockPrice(
            String stockCode,
            String stockName,
            long price,
            long change,
            double changeRate
    ) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.price = price;
        this.change = change;
        this.changeRate = changeRate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }

    public double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(double changeRate) {
        this.changeRate = changeRate;
    }
}
