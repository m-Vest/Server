package mvest.core.auth.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import mvest.core.auth.annotation.UserId;
import mvest.core.auth.application.AuthService;
import mvest.core.auth.constant.AuthConstant;
import mvest.core.auth.dto.request.PlatformRequestDTO;
import mvest.core.auth.dto.request.UserSignupDTO;
import mvest.core.auth.dto.response.JwtTokenDTO;
import mvest.core.auth.dto.response.LoginResponseDTO;
import mvest.core.auth.dto.response.UserTokenDTO;
import mvest.core.global.code.CommonSuccessCode;
import mvest.core.global.dto.ResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseDTO<UserTokenDTO> signup(
            @NotBlank @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) final String platformToken,
            @Valid @RequestBody final UserSignupDTO userSignupDTO
    ) {
        return ResponseDTO.success(CommonSuccessCode.OK, authService.signup(platformToken, userSignupDTO));
    }

    @PostMapping("/login")
    public ResponseDTO<LoginResponseDTO> login(
            @NotBlank @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) final String platformToken,
            @Valid @RequestBody final PlatformRequestDTO platformRequestDTO
    ) {
        return ResponseDTO.success(CommonSuccessCode.OK, authService.login(platformRequestDTO, platformToken));
    }

    @PostMapping("/logout")
    public ResponseDTO<Void> logout(
            @UserId Long userId
    ) {
        authService.logout(userId);
        return ResponseDTO.success(CommonSuccessCode.OK);
    }

    @PostMapping("/withdraw")
    public ResponseDTO<Void> withdraw(
            @UserId Long userId
    ) {
        authService.withdraw(userId);
        return ResponseDTO.success(CommonSuccessCode.OK);
    }

    @PostMapping("/reissue")
    public ResponseDTO<JwtTokenDTO> reissue(
            @NotBlank @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) final String refreshToken
    ) {
        return ResponseDTO.success(CommonSuccessCode.OK, authService.reissue(refreshToken));
    }
}
