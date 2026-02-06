package mvest.core.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.core.auth.constant.AuthConstant;
import mvest.core.auth.dto.PlatformUserDTO;
import mvest.core.auth.infrastructure.KakaoFeignClient;
import mvest.core.auth.verification.KakaoUserDTO;
import mvest.core.user.domain.Platform;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {

    @Value("${oauth.kakao.admin-key}")
    private String adminKey;

    private final KakaoFeignClient kakaoFeignClient;

    public PlatformUserDTO getPlatformUserInfo(String platformToken) {
        KakaoUserDTO kakaoUserDTO = kakaoFeignClient.getUserInformation(platformToken);
        log.info("kakao user info = {}", kakaoUserDTO);
        return PlatformUserDTO.of(
                Platform.KAKAO,
                kakaoUserDTO.id().toString());
    }

    public void unlink(final String platformId) {
        kakaoFeignClient.unlinkUser(
                AuthConstant.GRANT_TYPE + adminKey,
                AuthConstant.TARGET_ID_TYPE,
                Long.valueOf(platformId)
        );
    }
}
