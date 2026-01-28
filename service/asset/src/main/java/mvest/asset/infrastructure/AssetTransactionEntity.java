package mvest.asset.infrastructure;

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
@Table(name = "asset_transactions")
public class AssetTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long orderId;

    private Long userId;
    private String stockCode;
    private String transactionType;
    private BigDecimal price;
    private int quantity;
    private BigDecimal cashChange;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public AssetTransactionEntity(Long id,
                                  Long orderId,
                                  Long userId,
                                  String stockCode,
                                  String transactionType,
                                  BigDecimal price,
                                  int quantity,
                                  BigDecimal cashChange) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.stockCode = stockCode;
        this.transactionType = transactionType;
        this.price = price;
        this.quantity = quantity;
        this.cashChange = cashChange;
    }
}
