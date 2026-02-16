package mvest.order.global.code;

public enum InfrastructureErrorCode implements ErrorCode {
    REDIS_PARSE_ERROR("Redis 데이터의 파싱 과정에서 오류가 발생했습니다.");

    private final String errorMessage;

    InfrastructureErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
