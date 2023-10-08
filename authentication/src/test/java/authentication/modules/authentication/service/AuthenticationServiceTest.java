package authentication.modules.authentication.service;

import authentication.modules.authentication.dto.request.SignInRequest;
import authentication.modules.authentication.dto.request.SignUpRequest;
import authentication.modules.authentication.service.impl.AuthenticationService;
import authentication.modules.authentication.service.impl.FirebaseAuthenticationService;
import authentication.modules.authentication.service.impl.JwtService;
import authentication.modules.authentication.service.impl.UserManagementService;
import authentication.modules.authentication.validation.AuthenticationValidator;
import authentication.modules.user.model.User;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static authentication.modules.authentication.helpers.SignInRequestHelper.defaultSignInRequest;
import static authentication.modules.authentication.helpers.SignUpRequestHelper.defaultSignUpRequest;
import static authentication.modules.user.helpers.UserHelper.userOne;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserManagementService userManagementService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private FirebaseAuthenticationService firebaseAuthenticationService;

    @Mock
    private AuthenticationValidator authenticationValidator;

    @Mock
    private FirebaseToken mockFirebaseToken;

    private SignInRequest signInRequest;
    private SignUpRequest signUpRequest;
    private User mockUser;
    private String mockJwt;

    @BeforeEach
    void setUp() {
        signInRequest = defaultSignInRequest();
        signUpRequest = defaultSignUpRequest();
        mockUser = userOne();
        mockJwt = "mockJwtToken";
    }

    @Test
    void signup_shouldReturnJwtResponse_whenRequestIsValid() {
        when(userManagementService.createUser(any(SignUpRequest.class))).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class))).thenReturn(mockJwt);

        var response = authenticationService.signup(signUpRequest);

        assertEquals(mockJwt, response.getToken());
    }

    @Test
    void signup_shouldThrowException_whenUserCreationFails() {
        when(userManagementService.createUser(any(SignUpRequest.class)))
                .thenThrow(new RuntimeException("User creation failed"));

        assertThrows(RuntimeException.class, () -> {
            authenticationService.signup(signUpRequest);
        });
    }

    @Test
    void signup_shouldThrowException_whenTokenGenerationFails() {
        when(userManagementService.createUser(any(SignUpRequest.class))).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class)))
                .thenThrow(new RuntimeException("Token generation failed"));

        assertThrows(RuntimeException.class, () -> {
            authenticationService.signup(signUpRequest);
        });
    }

    @Test
    void signin_shouldReturnJwtResponse_whenCredentialsAreValid() {
        lenient().when(jwtService.generateToken(any(User.class))).thenReturn(mockJwt);
        when(userManagementService.getUserByEmail(signInRequest.getEmail())).thenReturn(mockUser);

        var response = authenticationService.signin(signInRequest);

        assertEquals(mockJwt, response.getToken());
    }

    @Test
    void signin_shouldThrowException_whenAuthenticationFails() {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {
                });

        assertThrows(AuthenticationException.class, () -> {
            authenticationService.signin(signInRequest);
        });
    }

    @Test
    void signin_shouldThrowException_whenUserNotFound() {
        when(userManagementService.getUserByEmail(anyString()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.signin(signInRequest);
        });
    }

    @Test
    void signin_shouldThrowException_whenTokenGenerationFails() {
        when(userManagementService.getUserByEmail(anyString())).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class)))
                .thenThrow(new RuntimeException("Token generation failed"));

        assertThrows(RuntimeException.class, () -> {
            authenticationService.signin(signInRequest);
        });
    }

    @Test
    void authenticateWithOAuth_shouldReturnJwtResponse_whenFirebaseTokenIsValid() {
        var firebaseToken = "mockFirebaseToken";

        when(firebaseAuthenticationService.verifyToken(anyString())).thenReturn(mockFirebaseToken);
        when(mockFirebaseToken.getEmail()).thenReturn("mock@email.com");
        when(userManagementService.getUserByEmail(anyString())).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class))).thenReturn(mockJwt);

        var response = authenticationService.authenticateWithOAuth(firebaseToken);

        assertEquals(mockJwt, response.getToken());
    }

    @Test
    void authenticateWithOAuth_shouldThrowException_whenFirebaseTokenIsInvalid() {
        doThrow(new IllegalArgumentException("Invalid Firebase token"))
                .when(authenticationValidator).validateFirebaseToken(anyString());

        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.authenticateWithOAuth("invalidFirebaseToken");
        });
    }

    @Test
    void authenticateWithOAuth_shouldThrowException_whenTokenVerificationFails() {
        when(firebaseAuthenticationService.verifyToken(anyString()))
                .thenThrow(new RuntimeException("Token verification failed"));

        assertThrows(RuntimeException.class, () -> {
            authenticationService.authenticateWithOAuth("mockFirebaseToken");
        });
    }

    @Test
    void authenticateWithOAuth_shouldThrowException_whenUserNotFound() {
        when(firebaseAuthenticationService.verifyToken(anyString())).thenReturn(mockFirebaseToken);
        when(userManagementService.getUserByEmail(null))
                .thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.authenticateWithOAuth("mockFirebaseToken");
        });
    }

    @Test
    void authenticateWithOAuth_shouldThrowException_whenTokenGenerationFails() {
        when(firebaseAuthenticationService.verifyToken(anyString())).thenReturn(mockFirebaseToken);
        when(userManagementService.getUserByEmail(anyString())).thenReturn(mockUser);
        when(jwtService.generateToken(any(User.class)))
                .thenThrow(new RuntimeException("Token generation failed"));

        assertThrows(RuntimeException.class, () -> {
            authenticationService.authenticateWithOAuth("mockFirebaseToken");
        });
    }
}
