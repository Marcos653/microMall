package authentication.modules.user.service;

import authentication.modules.user.service.impl.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Test
    void createToken_ShouldReturnUUID() {
        var token = tokenService.createToken();

        assertNotNull(token);
        assertTrue(token.matches(
                "[a-f0-9]{8}-[a-f0-9]{4}-[1-5][a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}"));
    }

    @Test
    void calculateExpiryDate_ShouldReturnDateInFuture() {
        var expiryDate = tokenService.calculateExpiryDate();

        assertNotNull(expiryDate);

        var cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 9);

        assertTrue(expiryDate.after(cal.getTime()));
    }
}

