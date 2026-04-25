package si.rsj.pu.service.exception.common;

public abstract class AppException extends RuntimeException {
    protected AppException(String message) {
        super(message);
    }
}