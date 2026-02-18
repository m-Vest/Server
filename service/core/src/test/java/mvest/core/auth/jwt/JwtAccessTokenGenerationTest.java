package mvest.core.auth.jwt;

import mvest.core.auth.dto.ClaimDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtAccessTokenGenerationTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();

        ReflectionTestUtils.setField(jwtTokenProvider,
                "secretKey",
                "test-secret-key-test-secret-key");

        ReflectionTestUtils.setField(jwtTokenProvider,
                "accessTokenExpirationTime",
                60_000L);

        ReflectionTestUtils.setField(jwtTokenProvider,
                "refreshTokenExpirationTime",
                120_000L);

        ReflectionTestUtils.setField(jwtTokenProvider,
                "signupTokenExpirationTime",
                30_000L);

        jwtTokenProvider.afterPropertiesSet();
    }

    @Test
    @DisplayName("userId=1인 ACCESS 토큰 생성 성공")
    void generateAccessToken_userId1_success() {

        // given
        Long userId = 1L;

        // when
        String accessToken =
                jwtTokenProvider.generateTokenPair(userId).accessToken();

        ClaimDTO claim =
                jwtTokenProvider.getClaimFromToken(accessToken);

        // then
        assertThat(accessToken).isNotNull();
        assertThat(claim.userId()).isEqualTo(1L);
        assertThat(claim.tokenType()).isEqualTo(TokenType.ACCESS);

        System.out.println("ACCESS TOKEN: " + accessToken);
    }
}
