package authentication.modules.user.helpers;

import authentication.modules.user.enums.ERole;
import authentication.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public class UserHelper {

    public static User userOne() {
        return User.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test.user@example.com")
                .password("password")
                .role(ERole.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static User userTwo() {
        return User.builder()
                .id(2L)
                .firstName("Test")
                .lastName("User")
                .email("test.user2@example.com")
                .password("password")
                .role(ERole.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static User userThree() {
        return User.builder()
                .id(3L)
                .firstName("Test")
                .lastName("User")
                .email("test.user3@example.com")
                .password("password")
                .role(ERole.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static List<User> usersList() {
        return List.of(userOne(), userTwo(), userThree());
    }

    public static Page<User> usersPage() {
        return new PageImpl<>(usersList());
    }
}
