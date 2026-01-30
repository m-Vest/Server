package mvest.core.auth.application;

import mvest.core.auth.dto.request.PlatformRequestDTO;
import mvest.core.auth.dto.request.UserSignupDTO;
import mvest.core.auth.dto.response.JwtTokenDTO;
import mvest.core.auth.dto.response.LoginResponseDTO;
import mvest.core.auth.dto.response.UserTokenDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public UserTokenDTO signup(String platformToken, UserSignupDTO userSignupDTO) {
        return null;
    }

    public LoginResponseDTO login(PlatformRequestDTO platformRequestDTO, String platformToken) {
        return null;
    }

    public void logout(Long userId) {
    }

    public JwtTokenDTO reissue(String refreshToken) {
        return null;
    }

    public void withdraw(Long userId) {
    }
}
