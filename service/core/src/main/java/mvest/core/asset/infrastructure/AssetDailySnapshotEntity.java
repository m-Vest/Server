package mvest.core.asset.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "asset_daily_snapshot",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_date", columnNames = {"user_id", "snapshot_date"})
        }
)
@Getter
@NoArgsConstructor
public class AssetDailySnapshotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;

    @Column(name = "total_asset", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalAsset;

    @Column(name = "cash_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal cashAmount;

    @Column(name = "stock_evaluation_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal stockEvaluationAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public AssetDailySnapshotEntity(
            Long userId,
            LocalDate snapshotDate,
            BigDecimal totalAsset,
            BigDecimal cashAmount,
            BigDecimal stockEvaluationAmount
    ) {
        this.userId = userId;
        this.snapshotDate = snapshotDate;
        this.totalAsset = totalAsset;
        this.cashAmount = cashAmount;
        this.stockEvaluationAmount = stockEvaluationAmount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(
            BigDecimal totalAsset,
            BigDecimal cashAmount,
            BigDecimal stockEvaluationAmount
    ) {
        this.totalAsset = totalAsset;
        this.cashAmount = cashAmount;
        this.stockEvaluationAmount = stockEvaluationAmount;
        this.updatedAt = LocalDateTime.now();
    }
}
