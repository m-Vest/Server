package mvest.core.global.exception;

import mvest.core.global.code.CommonErrorCode;
import mvest.core.global.dto.ResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("BusinessException 처리 테스트")
    void handleBusinessException() {
        // given
        BusinessException exception = new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR);

        // when
        ResponseDTO<Void> response = exceptionHandler.handleBusinessException(exception);

        // then
        assertThat(response.code()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.message()).isEqualTo(CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        assertThat(response.data()).isNull();
    }
}
