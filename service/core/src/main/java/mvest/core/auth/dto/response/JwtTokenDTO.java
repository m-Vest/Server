package mvest.core.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record JwtTokenDTO(
        String accessToken,
        String refreshToken
) {

    public static JwtTokenDTO of(String accessToken, String refreshToken) {
        return JwtTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
