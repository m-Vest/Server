package mvest.asset.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "user_stocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "stock_code"})
        }
)
public class UserStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String stockCode;
    private int quantity;

    @Builder
    public UserStockEntity(Long id,
                           Long userId,
                           String stockCode,
                           int quantity) {
        this.id = id;
        this.userId = userId;
        this.stockCode = stockCode;
        this.quantity = quantity;
    }
}
