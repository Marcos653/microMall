package authentication.modules.authentication.controller;

import authentication.modules.authentication.controller.contract.IAuthenticationController;
import authentication.modules.authentication.dto.request.SignInRequest;
import authentication.modules.authentication.dto.request.SignUpRequest;
import authentication.modules.authentication.dto.response.JwtAuthenticationResponse;
import authentication.modules.authentication.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController implements IAuthenticationController {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> authenticateWithOAuth(@RequestBody String firebaseToken) {
        return ResponseEntity.ok(authenticationService.authenticateWithOAuth(firebaseToken));
    }
}
