package authentication.modules.common.dao.response;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private final String message;
    private final HttpStatus status;

    public ErrorDetails(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
