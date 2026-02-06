package mvest.core.auth.jwt;

import mvest.core.auth.dto.ClaimDTO;
import mvest.core.auth.dto.PlatformUserDTO;
import mvest.core.auth.dto.response.JwtTokenDTO;
import mvest.core.user.domain.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();

        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "test-secret-key-test-secret-key");
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenExpirationTime", 60_000L);
        ReflectionTestUtils.setField(jwtTokenProvider, "refreshTokenExpirationTime", 120_000L);
        ReflectionTestUtils.setField(jwtTokenProvider, "signupTokenExpirationTime", 30_000L);

        jwtTokenProvider.afterPropertiesSet();
    }

    @Test
    @DisplayName("ACCESS / REFRESH 토큰 생성 및 Claim 파싱 성공")
    void generateAccessAndRefreshToken_success() {
        // given
        Long userId = 1L;

        // when
        JwtTokenDTO tokenPair = jwtTokenProvider.generateTokenPair(userId);
        ClaimDTO accessClaim = jwtTokenProvider.getClaimFromToken(tokenPair.accessToken());
        ClaimDTO refreshClaim = jwtTokenProvider.getClaimFromToken(tokenPair.refreshToken());

        // then
        assertThat(accessClaim.userId()).isEqualTo(userId);
        assertThat(accessClaim.tokenType()).isEqualTo(TokenType.ACCESS);

        assertThat(refreshClaim.userId()).isEqualTo(userId);
        assertThat(refreshClaim.tokenType()).isEqualTo(TokenType.REFRESH);
    }

    @Test
    @DisplayName("SIGNUP 토큰 생성 및 Claim 파싱 성공")
    void generateSignupToken_success() {
        // given
        PlatformUserDTO platformUser =
                new PlatformUserDTO(Platform.KAKAO, "kakao-123");

        // when
        String signupToken = jwtTokenProvider.generateSignupToken(platformUser);
        ClaimDTO claim = jwtTokenProvider.getClaimFromToken(signupToken);

        // then
        assertThat(claim.userId()).isNull();
        assertThat(claim.platform()).isEqualTo("KAKAO");
        assertThat(claim.platformId()).isEqualTo("kakao-123");
        assertThat(claim.tokenType()).isEqualTo(TokenType.SIGNUP);
    }
}
