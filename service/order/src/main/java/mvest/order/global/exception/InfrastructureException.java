package mvest.order.global.exception;

import mvest.order.global.code.ErrorCode;

public class InfrastructureException extends RuntimeException {
    private final ErrorCode errorCode;

    public InfrastructureException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
