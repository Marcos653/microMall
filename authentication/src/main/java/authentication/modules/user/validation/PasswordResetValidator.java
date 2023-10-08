package authentication.modules.user.validation;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.common.exception.TokenExpiredException;
import authentication.modules.user.model.PasswordResetToken;
import authentication.modules.user.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PasswordResetValidator {

    private static final String USER_NOT_FOUND_ERROR = "User not found";
    private static final String TOKEN_NOT_FOUND_ERROR = "Token not found";
    private static final String TOKEN_EXPIRED_ERROR = "Token expired";

    public User validateUser(Optional<User> userOptional) {
        return userOptional.orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_ERROR));
    }

    public PasswordResetToken validateResetToken(Optional<PasswordResetToken> resetTokenOptional) {
        return resetTokenOptional.orElseThrow(() -> new NotFoundException(TOKEN_NOT_FOUND_ERROR));
    }

    public void validateTokenExpiry(PasswordResetToken resetToken) {
        if (resetToken.isExpired()) {
            throw new TokenExpiredException(TOKEN_EXPIRED_ERROR);
        }
    }
}
