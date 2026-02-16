package mvest.core.global.code;

public enum DomainErrorCode implements ErrorMessageCode {
    ASSET_NOT_FOUND("사용자의 자산을 확인할 수 없습니다."),
    INSUFFICIENT_BALANCE("사용자의 잔고가 충분하지 않습니다.");

    private final String errorMessage;

    DomainErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
