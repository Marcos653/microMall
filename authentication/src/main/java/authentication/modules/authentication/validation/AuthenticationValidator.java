package authentication.modules.authentication.validation;

import authentication.modules.authentication.service.impl.FirebaseAuthenticationService;
import authentication.modules.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationValidator {

    private static final String USER_NOT_FOUND_ERROR = "User not found. Please create an account.";

    private final FirebaseAuthenticationService firebaseAuthenticationService;

    public void validateFirebaseToken(String firebaseToken) {
        var decodedToken = firebaseAuthenticationService.verifyToken(firebaseToken);
        if (decodedToken == null) {
            throw new NotFoundException(USER_NOT_FOUND_ERROR);
        }
    }
}