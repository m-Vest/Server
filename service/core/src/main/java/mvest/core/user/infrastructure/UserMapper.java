package mvest.core.user.infrastructure;

import mvest.core.user.domain.User;

public final class UserMapper {

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getUserId(),
                entity.getPlatform(),
                entity.getPlatformId(),
                entity.getUserName(),
                entity.getBirth(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserEntity toEntity(User domain) {
        return UserEntity.builder()
                .userId(domain.getUserId())
                .platform(domain.getPlatform())
                .platformId(domain.getPlatformId())
                .userName(domain.getUserName())
                .birth(domain.getBirth())
                .build();
    }
}
