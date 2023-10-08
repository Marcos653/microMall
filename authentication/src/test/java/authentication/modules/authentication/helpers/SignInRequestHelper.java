package authentication.modules.authentication.helpers;

import authentication.modules.authentication.dto.request.SignInRequest;

public class SignInRequestHelper {

    public static SignInRequest createSignInRequest(String email, String password) {
        return SignInRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

    public static SignInRequest defaultSignInRequest() {
        return createSignInRequest("test.user@example.com", "password123");
    }
}
