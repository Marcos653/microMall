package authentication.modules.authentication.service;

import authentication.modules.authentication.service.impl.JwtService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static final String MOCK_JWT_SIGNING_KEY = "ThisIsASampleKeyForJwtSigning123";


    private JwtService jwtService;

    @Mock
    private UserDetails mockUserDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(MOCK_JWT_SIGNING_KEY);
    }

    @Test
    void generateToken_shouldReturnValidToken_whenUserDetailsProvided() {
        String username = "mockUser";
        when(mockUserDetails.getUsername()).thenReturn(username);

        String token = jwtService.generateToken(mockUserDetails);

        assertNotNull(token);
        assertEquals(username, jwtService.extractUserName(token));
    }

    @Test
    void extractUserName_shouldReturnCorrectUsername_whenValidTokenProvided() {
        String username = "mockUser";
        when(mockUserDetails.getUsername()).thenReturn(username);

        String token = jwtService.generateToken(mockUserDetails);
        String extractedUsername = jwtService.extractUserName(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void isTokenValid_shouldReturnTrue_whenTokenAndUserDetailsMatch() {
        String username = "validUser";
        when(mockUserDetails.getUsername()).thenReturn(username);

        String token = jwtService.generateToken(mockUserDetails);

        assertTrue(jwtService.isTokenValid(token, mockUserDetails));
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenTokenIsExpired() {
        String username = "expiredUser";
        when(mockUserDetails.getUsername()).thenReturn(username);

        String token = jwtService.generateToken(mockUserDetails);
        // TODO: Manipulate the token or mock methods to simulate an expired token.

        assertFalse(jwtService.isTokenValid(token, mockUserDetails));
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenUsernameDoesNotMatch() {
        String username1 = "user1";
        String username2 = "user2";
        when(mockUserDetails.getUsername()).thenReturn(username1);

        String token = jwtService.generateToken(mockUserDetails);
        when(mockUserDetails.getUsername()).thenReturn(username2);

        assertFalse(jwtService.isTokenValid(token, mockUserDetails));
    }

    // TODO: Add more test cases for other scenarios and methods.
}