package authentication.modules.common.dao.response;

public class ErrorResponse {
    private final ErrorDetails errorDetails;

    public ErrorResponse(ErrorDetails errorDetails) {
        this.errorDetails = errorDetails;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }
}

