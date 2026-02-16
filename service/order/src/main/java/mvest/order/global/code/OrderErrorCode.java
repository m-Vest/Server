package mvest.order.global.code;

public enum OrderErrorCode implements ErrorCode {
    ORDER_NOT_FOUND("전달된 이벤트에서 주문 ID를 찾을 수 없습니다."),
    ORDER_ALREADY_EXISTS("중복 주문이 존재합니다.");

    private final String errorMessage;

    OrderErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
