package mvest.core.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import mvest.core.auth.constant.AuthConstant;
import mvest.core.auth.dto.ClaimDTO;
import mvest.core.auth.dto.PlatformUserDTO;
import mvest.core.auth.dto.response.JwtTokenDTO;
import mvest.core.global.code.AuthErrorCode;
import mvest.core.global.exception.AuthException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider implements InitializingBean {

    @Value("${jwt.user.access_token_expiration_time}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.user.refresh_token_expiration_time}")
    private Long refreshTokenExpirationTime;

    @Value("${jwt.user.signup_token_expiration_time}")
    private Long signupTokenExpirationTime;

    @Value("${jwt.user.secret}")
    private String secretKey;

    private Key signingKey;

    @Override
    public void afterPropertiesSet() {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.signingKey = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public JwtTokenDTO generateTokenPair(Long userId) {
        return JwtTokenDTO.of(
                generateUserToken(userId, TokenType.ACCESS),
                generateUserToken(userId, TokenType.REFRESH)
        );
    }

    public String generateSignupToken(PlatformUserDTO platformUser) {
        Date now = new Date();

        Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + signupTokenExpirationTime));

        claims.put("platform", platformUser.platform().name());
        claims.put("platformId", platformUser.platformId());
        claims.put(AuthConstant.TOKEN_TYPE, TokenType.SIGNUP.name());

        return buildToken(claims);
    }

    private String generateUserToken(Long userId, TokenType tokenType) {
        Date now = new Date();

        Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration(tokenType)));

        claims.put(AuthConstant.USER_ID, userId);
        claims.put(AuthConstant.TOKEN_TYPE, tokenType.name());

        return buildToken(claims);
    }

    private long expiration(TokenType tokenType) {
        return switch (tokenType) {
            case ACCESS -> accessTokenExpirationTime;
            case REFRESH -> refreshTokenExpirationTime;
            case SIGNUP -> signupTokenExpirationTime;
        };
    }

    private String buildToken(Claims claims) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(signingKey)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    public ClaimDTO getClaimFromToken(String token) {
        Claims claims = getClaims(token);

        TokenType tokenType =
                TokenType.valueOf(claims.get(AuthConstant.TOKEN_TYPE).toString());

        if (tokenType == TokenType.SIGNUP) {
            return ClaimDTO.signup(
                    claims.get("platform").toString(),
                    claims.get("platformId").toString(),
                    tokenType
            );
        }

        return ClaimDTO.access(
                Long.valueOf(claims.get(AuthConstant.USER_ID).toString()),
                tokenType
        );
    }
}
