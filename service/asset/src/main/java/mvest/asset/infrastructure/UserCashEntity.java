package mvest.asset.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvest.asset.global.code.DomainErrorCode;
import mvest.asset.global.exception.DomainException;
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

    public void increase(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void decrease(BigDecimal amount) {
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException(DomainErrorCode.INSUFFICIENT_BALANCE);
        }
        this.balance = newBalance;
    }
}
