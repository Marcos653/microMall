package authentication.modules.user.helpers;

import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.enums.ERole;

public class UserRequestHelper {

    public static UserRequest userRequestOne() {
        return UserRequest.builder()
                .firstName("Test")
                .lastName("User")
                .email("test.user@example.com")
                .password("password")
                .role(ERole.USER)
                .build();
    }

    public static UserRequest userRequestTwo() {
        return UserRequest.builder()
                .firstName("Test")
                .lastName("User")
                .email("test.user2@example.com")
                .password("password")
                .role(ERole.USER)
                .build();
    }

    public static UserRequest userRequestThree() {
        return UserRequest.builder()
                .firstName("Test")
                .lastName("User")
                .email("test.user3@example.com")
                .password("password")
                .role(ERole.USER)
                .build();
    }
}
