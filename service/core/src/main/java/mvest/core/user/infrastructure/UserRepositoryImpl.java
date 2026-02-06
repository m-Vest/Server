package mvest.core.user.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.core.auth.dto.request.UserSignupDTO;
import mvest.core.auth.dto.response.JwtTokenDTO;
import mvest.core.auth.infrastructure.TokenEntity;
import mvest.core.auth.infrastructure.TokenRedisRepository;
import mvest.core.user.application.UserRepository;
import mvest.core.user.domain.Platform;
import mvest.core.user.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final TokenRedisRepository tokenRedisRepository;

    @Override
    @Transactional
    public User create(Platform platform, String platformId, UserSignupDTO signupDTO) {
        UserEntity entity = UserEntity.builder()
                .platform(platform)
                .platformId(platformId)
                .userName(signupDTO.userName())
                .birth(signupDTO.birth())
                .build();

        return UserMapper.toDomain(
                userJpaRepository.save(entity)
        );
    }

    @Override
    public Optional<User> findByPlatform(Platform platform, String platformId) {
        return userJpaRepository
                .findByPlatformAndPlatformId(platform, platformId)
                .map(UserMapper::toDomain);
    }

    @Override
    public void saveToken(Long userId, JwtTokenDTO token) {
        TokenEntity tokenEntity = TokenEntity.builder()
                .id(userId)
                .refreshToken(token.refreshToken())
                .build();
        tokenRedisRepository.save(tokenEntity);
    }

    @Override
    public void deleteToken(Long userId) {
        tokenRedisRepository.deleteById(userId);
    }
}
