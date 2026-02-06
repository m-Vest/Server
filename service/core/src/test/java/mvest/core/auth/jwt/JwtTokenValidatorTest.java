package mvest.core.auth.jwt;

import mvest.core.auth.dto.PlatformUserDTO;
import mvest.core.global.exception.AuthException;
import mvest.core.user.domain.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenValidatorTest {

    private JwtTokenProvider jwtTokenProvider;
    private JwtTokenValidator jwtTokenValidator;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();

        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "test-secret-key-test-secret-key");
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenExpirationTime", 60_000L);
        ReflectionTestUtils.setField(jwtTokenProvider, "refreshTokenExpirationTime", 120_000L);
        ReflectionTestUtils.setField(jwtTokenProvider, "signupTokenExpirationTime", 30_000L);

        jwtTokenProvider.afterPropertiesSet();
        jwtTokenValidator = new JwtTokenValidator(jwtTokenProvider);
    }

    @Test
    @DisplayName("ACCESS 토큰 검증 성공")
    void validateAccessToken_success() {
        // given
        String accessToken =
                jwtTokenProvider.generateTokenPair(1L).accessToken();

        // when & then
        jwtTokenValidator.validateAccessToken(accessToken);
    }

    @Test
    @DisplayName("REFRESH 토큰 검증 성공")
    void validateRefreshToken_success() {
        // given
        String refreshToken =
                jwtTokenProvider.generateTokenPair(1L).refreshToken();

        // when & then
        jwtTokenValidator.validateRefreshToken(refreshToken);
    }

    @Test
    @DisplayName("SIGNUP 토큰 검증 성공")
    void validateSignupToken_success() {
        // given
        String signupToken =
                jwtTokenProvider.generateSignupToken(
                        new PlatformUserDTO(Platform.KAKAO, "kakao-123")
                );

        // when & then
        jwtTokenValidator.validateSignupToken(signupToken);
    }

    @Test
    @DisplayName("ACCESS 토큰을 REFRESH validator로 검증하면 실패")
    void validateAccessTokenWithRefreshValidator_fail() {
        // given
        String accessToken =
                jwtTokenProvider.generateTokenPair(1L).accessToken();

        // when & then
        assertThatThrownBy(() ->
                jwtTokenValidator.validateRefreshToken(accessToken)
        ).isInstanceOf(AuthException.class);
    }

    @Test
    @DisplayName("SIGNUP 토큰을 ACCESS validator로 검증하면 실패")
    void validateSignupTokenWithAccessValidator_fail() {
        // given
        String signupToken =
                jwtTokenProvider.generateSignupToken(
                        new PlatformUserDTO(Platform.KAKAO, "kakao-123")
                );

        // when & then
        assertThatThrownBy(() ->
                jwtTokenValidator.validateAccessToken(signupToken)
        ).isInstanceOf(AuthException.class);
    }

    @Test
    @DisplayName("null 토큰은 검증 실패")
    void validateNullToken_fail() {
        // given
        String token = null;

        // when & then
        assertThatThrownBy(() ->
                jwtTokenValidator.validateAccessToken(token)
        ).isInstanceOf(AuthException.class);
    }

    @Test
    @DisplayName("만료된 토큰은 검증 실패")
    void validateExpiredToken_fail() throws InterruptedException {
        // given
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenExpirationTime", 1L);
        String expiredToken =
                jwtTokenProvider.generateTokenPair(1L).accessToken();

        Thread.sleep(5);

        // when & then
        assertThatThrownBy(() ->
                jwtTokenValidator.validateAccessToken(expiredToken)
        ).isInstanceOf(AuthException.class);
    }

    @Test
    @DisplayName("서명이 위조된 토큰은 검증 실패")
    void validateTamperedToken_fail() {
        // given
        String token =
                jwtTokenProvider.generateTokenPair(1L).accessToken();
        String tamperedToken = token + "broken";

        // when & then
        assertThatThrownBy(() ->
                jwtTokenValidator.validateAccessToken(tamperedToken)
        ).isInstanceOf(AuthException.class);
    }
}
