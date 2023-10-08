package authentication.modules.user.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static authentication.modules.user.helpers.UserHelper.userOne;
import static org.junit.jupiter.api.Assertions.*;

class PasswordResetTokenTest {

    @Test
    void constructor_shouldSetFields() {
        var token = "testToken";
        var user = userOne();
        var passwordResetToken = new PasswordResetToken(token, user);

        assertEquals(token, passwordResetToken.getToken());
        assertEquals(user, passwordResetToken.getUser());
    }

    @Test
    void isExpired_shouldReturnTrue_whenExpiryDateIsInPast() {
        var passwordResetToken = new PasswordResetToken();
        passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() - 10000));

        assertTrue(passwordResetToken.isExpired());
    }

    @Test
    void isExpired_shouldReturnFalse_whenExpiryDateIsInFuture() {
        var passwordResetToken = new PasswordResetToken();
        passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + 10000));

        assertFalse(passwordResetToken.isExpired());
    }
}
