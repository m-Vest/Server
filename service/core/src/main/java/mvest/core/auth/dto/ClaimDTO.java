package mvest.core.auth.dto;

import mvest.core.auth.jwt.TokenType;

public record ClaimDTO(
        Long userId,
        String platform,
        String platformId,
        TokenType tokenType
) {
    public static ClaimDTO access(Long userId, TokenType tokenType) {
        return new ClaimDTO(userId, null, null, tokenType);
    }

    public static ClaimDTO signup(String platform, String platformId, TokenType tokenType) {
        return new ClaimDTO(null, platform, platformId, tokenType);
    }
}
