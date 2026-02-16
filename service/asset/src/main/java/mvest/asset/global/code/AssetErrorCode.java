package mvest.asset.global.code;

public enum AssetErrorCode implements ErrorCode {
    ORDER_NOT_FOUND("전달된 이벤트에서 주문 ID를 찾을 수 없습니다."),
    INSUFFICIENT_BALANCE("사용자의 잔고가 충분하지 않습니다.");

    private final String errorMessage;

    AssetErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
