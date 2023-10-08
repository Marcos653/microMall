package authentication.modules.authentication.service;

import authentication.modules.authentication.service.impl.FirebaseAuthenticationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirebaseAuthenticationServiceTest {

    @InjectMocks
    private FirebaseAuthenticationService firebaseAuthenticationService;

    @Mock
    private FirebaseAuth firebaseAuth;

    @Mock
    private FirebaseToken mockToken;

    @BeforeEach
    void setUp() {
        mockStatic(FirebaseAuth.class);
        when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);
    }

    @Test
    void verifyToken_shouldReturnValidToken_whenTokenIsValid() throws FirebaseAuthException {
        when(mockToken.getUid()).thenReturn("mockUid");
        when(firebaseAuth.verifyIdToken("validToken")).thenReturn(mockToken);

        var returnedToken = firebaseAuthenticationService.verifyToken("validToken");

        assertEquals("mockUid", returnedToken.getUid());
    }
}
