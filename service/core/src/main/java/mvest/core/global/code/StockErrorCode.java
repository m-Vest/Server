package mvest.core.global.code;

import org.springframework.http.HttpStatus;

public enum StockErrorCode implements ErrorCode {
    STOCK_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "주식 현재가 리스트 조회에 실패했습니다."),
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "현재 주식의 현재가 조회에 실패했습니다."),
    STOCK_PARSING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "주식 데이터의 파싱에 실패하였습니다.");

    private final HttpStatus status;
    private final String errorMessage;

    StockErrorCode(HttpStatus httpStatus, String errorMessage) {
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
