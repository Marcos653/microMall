package authentication.modules.authentication.service.impl;

import authentication.modules.authentication.dto.request.SignInRequest;
import authentication.modules.authentication.dto.request.SignUpRequest;
import authentication.modules.authentication.dto.response.JwtAuthenticationResponse;
import authentication.modules.authentication.service.IAuthenticationService;
import authentication.modules.authentication.validation.AuthenticationValidator;
import authentication.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserManagementService userManagementService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FirebaseAuthenticationService firebaseAuthenticationService;
    private final AuthenticationValidator authenticationValidator;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = userManagementService.createUser(request);

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userManagementService.getUserByEmail(request.getEmail());
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse authenticateWithOAuth(String firebaseToken) {
        authenticationValidator.validateFirebaseToken(firebaseToken);

        var userEmail = firebaseAuthenticationService.verifyToken(firebaseToken).getEmail();
        var user = userManagementService.getUserByEmail(userEmail);

        setAuthentication(user);

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private void setAuthentication(User user) {
        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
