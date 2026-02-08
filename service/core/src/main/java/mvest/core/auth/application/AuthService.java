package mvest.core.auth.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.EventType;
import mvest.common.event.payload.UserRegisteredEventPayload;
import mvest.common.event.payload.UserWithdrawnEventPayload;
import mvest.common.outboxmessagerelay.OutboxEventPublisher;
import mvest.core.auth.dto.ClaimDTO;
import mvest.core.auth.dto.PlatformUserDTO;
import mvest.core.auth.dto.request.UserSignupDTO;
import mvest.core.auth.dto.response.JwtTokenDTO;
import mvest.core.auth.dto.response.UserTokenDTO;
import mvest.core.auth.jwt.JwtTokenProvider;
import mvest.core.auth.jwt.JwtTokenValidator;
import mvest.core.global.code.AuthErrorCode;
import mvest.core.global.exception.AuthException;
import mvest.core.user.application.UserRepository;
import mvest.core.user.domain.Platform;
import mvest.core.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenValidator jwtTokenValidator;
    private final KakaoService kakaoService;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public UserTokenDTO signup(String signupToken, UserSignupDTO userSignupDTO) {
        signupToken = resolveToken(signupToken);
        jwtTokenValidator.validateSignupToken(signupToken);
        ClaimDTO claim = jwtTokenProvider.getClaimFromToken(signupToken);
        Platform platform = Platform.valueOf(claim.platform());
        String platformId = claim.platformId();

        userRepository.findByPlatform(platform, platformId)
                .ifPresent(user -> {
                    throw new AuthException(AuthErrorCode.USER_ALREADY_EXISTS);
                });

        User user = userRepository.create(
                platform,
                platformId,
                userSignupDTO
        );

        outboxEventPublisher.publish(
                EventType.USER_REGISTERED,
                UserRegisteredEventPayload.builder()
                        .userId(user.getUserId())
                        .registeredAt(user.getCreatedAt())
                        .build()
        );

        JwtTokenDTO token = jwtTokenProvider.generateTokenPair(user.getUserId());
        userRepository.saveToken(user.getUserId(), token);

        return UserTokenDTO.authenticated(user, token);
    }

    public UserTokenDTO login(String platformToken, Platform platform) {
        PlatformUserDTO platformUser = getPlatformInfo(platform, platformToken);
        return userRepository
                .findByPlatform(platformUser.platform(), platformUser.platformId())
                .map(this::authenticate)
                .orElseGet(() -> signupRequired(platformUser));
    }

    private UserTokenDTO authenticate(User user) {
        JwtTokenDTO token = jwtTokenProvider.generateTokenPair(user.getUserId());
        userRepository.saveToken(user.getUserId(), token);
        return UserTokenDTO.authenticated(user, token);
    }

    private UserTokenDTO signupRequired(PlatformUserDTO platformUser) {
        String signupToken = jwtTokenProvider.generateSignupToken(platformUser);
        return UserTokenDTO.signupRequired(signupToken);
    }

    public void logout(Long userId) {
        userRepository.deleteToken(userId);
    }

    public void withdraw(Long userId) {
        User user = userRepository.findById(userId);
        if(user.getPlatform() == Platform.KAKAO) {
            kakaoService.unlink(user.getPlatformId());
        } else {
            throw new AuthException(AuthErrorCode.PLATFORM_NOT_FOUND);
        }
        userRepository.deleteById(userId);

        outboxEventPublisher.publish(
                EventType.USER_WITHDRAWN,
                UserWithdrawnEventPayload.builder()
                        .userId(userId)
                        .withdrawnAt(java.time.LocalDateTime.now())
                        .build()
        );
    }

    public JwtTokenDTO reissue(String refreshToken) {
        jwtTokenValidator.validateRefreshToken(refreshToken);
        ClaimDTO claim = jwtTokenProvider.getClaimFromToken(refreshToken);
        Long userId = claim.userId();
        String savedRefreshToken = userRepository.findRefreshToken(userId)
                .orElseThrow(() ->
                        new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN)
                );
        if (!savedRefreshToken.equals(refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        JwtTokenDTO newToken = jwtTokenProvider.generateTokenPair(userId);
        userRepository.saveToken(userId, newToken);

        return newToken;
    }

    private PlatformUserDTO getPlatformInfo(Platform platform, String platformToken) {
        if (platform == Platform.KAKAO) {
            return kakaoService.getPlatformUserInfo(platformToken);
        }
        throw new AuthException(AuthErrorCode.PLATFORM_NOT_FOUND);
    }

    private String resolveToken(String token) {
        if (token == null || token.isBlank()) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }

        token = token.trim();

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return token;
    }
}
