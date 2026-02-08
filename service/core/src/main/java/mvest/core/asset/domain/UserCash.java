package mvest.core.asset.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserCash {

    private final Long userId;
    private final BigDecimal balance;
    private final LocalDateTime updatedAt;

    public UserCash(Long userId,
                    BigDecimal balance,
                    LocalDateTime updatedAt) {
        this.userId = userId;
        this.balance = balance;
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static UserCash initial(Long userId, BigDecimal balance) {
        return new UserCash(userId, balance, null);
    }
}
