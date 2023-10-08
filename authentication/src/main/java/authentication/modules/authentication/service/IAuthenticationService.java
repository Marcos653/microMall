package authentication.modules.authentication.service;

import authentication.modules.authentication.dto.request.SignInRequest;
import authentication.modules.authentication.dto.request.SignUpRequest;
import authentication.modules.authentication.dto.response.JwtAuthenticationResponse;

public interface IAuthenticationService {

    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);

    JwtAuthenticationResponse authenticateWithOAuth(String firebaseToken);
}
