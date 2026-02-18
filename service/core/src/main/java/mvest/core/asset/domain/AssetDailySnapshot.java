package mvest.core.asset.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AssetDailySnapshot {

    private final Long id;
    private final Long userId;
    private final LocalDate snapshotDate;
    private final BigDecimal totalAsset;
    private final BigDecimal cashAmount;
    private final BigDecimal stockEvaluationAmount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public AssetDailySnapshot(
            Long id,
            Long userId,
            LocalDate snapshotDate,
            BigDecimal totalAsset,
            BigDecimal cashAmount,
            BigDecimal stockEvaluationAmount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.snapshotDate = snapshotDate;
        this.totalAsset = totalAsset;
        this.cashAmount = cashAmount;
        this.stockEvaluationAmount = stockEvaluationAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public LocalDate getSnapshotDate() { return snapshotDate; }
    public BigDecimal getTotalAsset() { return totalAsset; }
    public BigDecimal getCashAmount() { return cashAmount; }
    public BigDecimal getStockEvaluationAmount() { return stockEvaluationAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public AssetDailySnapshot update(
            BigDecimal totalAsset,
            BigDecimal cashAmount,
            BigDecimal stockEvaluationAmount
    ) {
        return new AssetDailySnapshot(
                this.id,
                this.userId,
                this.snapshotDate,
                totalAsset,
                cashAmount,
                stockEvaluationAmount,
                this.createdAt,
                LocalDateTime.now()
        );
    }
}
