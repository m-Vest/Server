package mvest.core.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import mvest.core.user.domain.Platform;

public record AuthLoginRequestDTO(
        @NotNull(message = "플랫폼은 필수 값입니다.") Platform platform
) {
}
