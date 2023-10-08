package authentication.modules.user.dto.response;

import org.junit.jupiter.api.Test;

import static authentication.modules.user.helpers.UserHelper.userOne;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserResponseTest {

    @Test
    void of_shouldCopyPropertiesFromUser() {
        var user = userOne();
        var response = UserResponse.of(user);

        assertEquals(response.getId(), user.getId());
        assertEquals(response.getFirstName(), user.getFirstName());
        assertEquals(response.getLastName(), user.getLastName());
        assertEquals(response.getEmail(), user.getEmail());
    }
}
