package authentication.modules.user.validation;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.common.exception.TokenExpiredException;
import authentication.modules.user.model.PasswordResetToken;
import authentication.modules.user.model.User;
import authentication.modules.user.validation.PasswordResetValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResetValidatorTest {

    private PasswordResetValidator validator;

    @Mock
    private User user;

    @Mock
    private PasswordResetToken resetToken;

    @BeforeEach
    void setUp() {
        validator = new PasswordResetValidator();
    }

    @Test
    void validateUser_shouldThrowNotFoundException_whenUserOptionalIsEmpty() {
        assertThrows(NotFoundException.class, () -> validator.validateUser(Optional.empty()));
    }

    @Test
    void validateResetToken_shouldThrowNotFoundException_whenResetTokenOptionalIsEmpty() {
        assertThrows(NotFoundException.class, () -> validator.validateResetToken(Optional.empty()));
    }

    @Test
    void validateTokenExpiry_shouldThrowTokenExpiredException_whenResetTokenIsExpired() {
        when(resetToken.isExpired()).thenReturn(true);

        assertThrows(TokenExpiredException.class, () -> validator.validateTokenExpiry(resetToken));
    }
}
