package mvest.core.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import mvest.core.auth.dto.ClaimDTO;
import mvest.core.global.code.AuthErrorCode;
import mvest.core.global.exception.AuthException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

    private final JwtTokenProvider jwtTokenProvider;

    public void validateAccessToken(String accessToken) {
        validate(accessToken, TokenType.ACCESS, AuthErrorCode.EMPTY_TOKEN);
    }

    public void validateRefreshToken(String refreshToken) {
        validate(refreshToken, TokenType.REFRESH, AuthErrorCode.EMPTY_REFRESH_TOKEN);
    }

    public void validateSignupToken(String signupToken) {
        validate(signupToken, TokenType.SIGNUP, AuthErrorCode.EMPTY_TOKEN);
    }

    private void validate(
            String token,
            TokenType expectedType,
            AuthErrorCode emptyTokenError
    ) {
        if (token == null) {
            throw new AuthException(emptyTokenError);
        }

        try {
            ClaimDTO claim = jwtTokenProvider.getClaimFromToken(token);

            if (claim.tokenType() != expectedType) {
                throw new AuthException(AuthErrorCode.INVALID_TOKEN_TYPE);
            }

        } catch (MalformedJwtException ex) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new AuthException(AuthErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new AuthException(emptyTokenError);
        }
    }
}
