package mvest.core.global.exception;

import mvest.core.global.code.ErrorMessageCode;

public class DomainException extends RuntimeException {
    private final ErrorMessageCode errorCode;

    public DomainException(ErrorMessageCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorMessageCode getErrorCode() {
        return errorCode;
    }
}
