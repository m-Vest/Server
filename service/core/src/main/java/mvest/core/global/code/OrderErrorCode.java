package mvest.core.global.code;

import org.springframework.http.HttpStatus;

public enum OrderErrorCode implements ErrorCode {
    INSUFFICIENT_BALANCE(HttpStatus.NOT_FOUND, "예수금이 부족합니다."),
    INSUFFICIENT_STOCK(HttpStatus.NOT_FOUND, "주식 보유 수가 부족합니다.");

    private final HttpStatus status;
    private final String errorMessage;

    OrderErrorCode(HttpStatus httpStatus, String errorMessage) {
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
