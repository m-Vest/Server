package mvest.core.auth.dto.response;

import mvest.core.user.domain.User;

public record UserTokenDTO(
        TokenResponseType type,
        User user,
        JwtTokenDTO jwtToken,
        String signupToken
) {

    public static UserTokenDTO authenticated(
            User user,
            JwtTokenDTO jwtToken
    ) {
        return new UserTokenDTO(
                TokenResponseType.AUTHENTICATED,
                user,
                jwtToken,
                null
        );
    }

    public static UserTokenDTO signupRequired(
            String signupToken
    ) {
        return new UserTokenDTO(
                TokenResponseType.SIGNUP_REQUIRED,
                null,
                null,
                signupToken
        );
    }
}
