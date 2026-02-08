package mvest.core.asset.infrastructure;


import mvest.core.asset.domain.UserCash;

public final class UserCashMapper {

    public static UserCash toDomain(UserCashEntity entity) {
        return new UserCash(
                entity.getUserId(),
                entity.getBalance(),
                entity.getUpdatedAt()
        );
    }

    public static UserCashEntity toEntity(UserCash domain) {
        return UserCashEntity.builder()
                .userId(domain.getUserId())
                .balance(domain.getBalance())
                .build();
    }
}
