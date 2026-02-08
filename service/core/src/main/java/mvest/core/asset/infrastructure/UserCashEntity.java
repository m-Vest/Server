package mvest.core.asset.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_cash")
public class UserCashEntity {

    @Id
    private Long userId;

    private BigDecimal balance;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public UserCashEntity(Long userId,
                          BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
