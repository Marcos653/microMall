package authentication.modules.user.helpers;

import authentication.modules.user.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class UserResponseHelper {

    public static UserResponse userResponseOne() {
        return UserResponse.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test.user@example.com")
                .build();
    }

    public static UserResponse userResponseTwo() {
        return UserResponse.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test.user2@example.com")
                .build();
    }

    public static UserResponse userResponseThree() {
        return UserResponse.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test.user3@example.com")
                .build();
    }

    public static List<UserResponse> userResponseList() {
        return List.of(userResponseOne(), userResponseTwo(), userResponseThree());
    }

    public static Page<UserResponse> userResponsePage() {
        return new PageImpl<>(userResponseList());
    }
}
