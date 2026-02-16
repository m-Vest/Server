package mvest.order.global.code;

public enum StockErrorCode implements ErrorCode {
    STOCK_NOT_FOUND("현재 주식의 현재가 조회에 실패했습니다.");

    private final String errorMessage;

    StockErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
