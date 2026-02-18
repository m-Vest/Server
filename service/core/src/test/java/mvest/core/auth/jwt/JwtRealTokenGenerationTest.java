package mvest.core.auth.jwt;

import mvest.core.auth.dto.response.JwtTokenDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtRealTokenGenerationTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("실제 ACCESS 토큰 생성")
    void generateRealAccessToken() {

        // userId = 1
        JwtTokenDTO tokenPair = jwtTokenProvider.generateTokenPair(1L);

        String accessToken = tokenPair.accessToken();

        System.out.println("===== REAL ACCESS TOKEN =====");
        System.out.println(accessToken);
        System.out.println("=============================");
    }
}
