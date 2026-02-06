package mvest.core.auth.dto;

import mvest.core.user.domain.Platform;

public record PlatformUserDTO(
        Platform platform,
        String platformId
) {

    public static PlatformUserDTO of(
            Platform platform,
            String platformId
    ) {
        return new PlatformUserDTO(platform, platformId);
    }
}
