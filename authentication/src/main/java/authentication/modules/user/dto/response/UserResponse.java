package authentication.modules.user.dto.response;

import authentication.modules.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.beans.BeanUtils.copyProperties;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    public static UserResponse of(User user) {
        var response = new UserResponse();
        copyProperties(user, response);

        return response;
    }
}
