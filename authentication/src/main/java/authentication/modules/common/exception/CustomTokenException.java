package authentication.modules.common.exception;

public class CustomTokenException extends RuntimeException {

    public CustomTokenException(String message) {
        super(message);
    }

    public CustomTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}

