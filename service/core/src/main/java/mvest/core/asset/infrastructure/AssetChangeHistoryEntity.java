package mvest.core.asset.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "asset_change_history")
public class AssetChangeHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Long userId;
    private String stockCode;
    private String changeType;
    private int stockChange;
    private BigDecimal cashChange;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public AssetChangeHistoryEntity(Long id,
                                    Long orderId,
                                    Long userId,
                                    String stockCode,
                                    String changeType,
                                    int stockChange,
                                    BigDecimal cashChange) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.stockCode = stockCode;
        this.changeType = changeType;
        this.stockChange = stockChange;
        this.cashChange = cashChange;
    }
}
