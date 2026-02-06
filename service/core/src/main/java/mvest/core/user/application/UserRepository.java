package mvest.core.user.application;

import mvest.core.auth.dto.response.JwtTokenDTO;
import mvest.core.user.domain.Platform;
import mvest.core.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByPlatform(Platform platform, String platformId);
    void saveToken(Long userId, JwtTokenDTO token);
}
