package mvest.core.global.code;

import org.springframework.http.HttpStatus;

public enum InfrastructureErrorCode implements ErrorCode {
    REDIS_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Redis 데이터의 파싱 과정에서 오류가 발생했습니다."),
    REDIS_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Redis 저장에 실패하였습니다."),
    NAVER_FETCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Naver Finance Open API 응답에 실패하였습니다.");

    private final HttpStatus status;
    private final String errorMessage;

    InfrastructureErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.status = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
