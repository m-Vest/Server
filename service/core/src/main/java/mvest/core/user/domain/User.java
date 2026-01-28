package mvest.core.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private final Long userId;
    private final Platform platform;
    private final String platformId;
    private final String userName;
    private final LocalDate birth;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public User(Long userId,
                Platform platform,
                String platformId,
                String userName,
                LocalDate birth,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.userId = userId;
        this.platform = platform;
        this.platformId = platformId;
        this.userName = userName;
        this.birth = birth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
