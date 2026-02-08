package mvest.core.global.exception;

import mvest.core.global.code.CommonErrorCode;
import mvest.core.global.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseDTO<Void> handleSampleException(BusinessException e) {
        return ResponseDTO.failure(e.getErrorCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseDTO<Void> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseDTO.failure(CommonErrorCode.INVALID_URL_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDTO<Void> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseDTO.failure(CommonErrorCode.METHOD_NOT_ALLOWED_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO<Void> handleInternalServerException(Exception e) {
        logger.error("Unhandled exception occurred: {}", e.getMessage(), e);
        return ResponseDTO.failure(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }
}
