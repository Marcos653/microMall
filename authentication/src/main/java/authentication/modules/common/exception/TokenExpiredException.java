package authentication.modules.common.exception;

public class TokenExpiredException extends  RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }
}
