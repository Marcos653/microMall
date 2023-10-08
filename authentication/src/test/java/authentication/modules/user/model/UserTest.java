package authentication.modules.user.model;

import org.junit.jupiter.api.Test;

import static authentication.modules.user.helpers.UserRequestHelper.userRequestOne;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void of_shouldCopyPropertiesFromUserRequest() {
        var request = userRequestOne();
        var user = User.of(request);

        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());
        assertEquals(request.getRole(), user.getRole());
    }
}