package authentication.modules.authentication.helpers;

import authentication.modules.authentication.dto.request.SignUpRequest;

public class SignUpRequestHelper {

    public static SignUpRequest createSignUpRequest(String firstName, String lastName, String email, String password) {
        return SignUpRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();
    }

    public static SignUpRequest defaultSignUpRequest() {
        return createSignUpRequest("John", "Doe", "test.user@example.com", "password123");
    }
}
