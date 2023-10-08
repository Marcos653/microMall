package authentication.modules.user.service;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.email.service.MailService;
import authentication.modules.user.helpers.UserHelper;
import authentication.modules.user.model.PasswordResetToken;
import authentication.modules.user.model.User;
import authentication.modules.user.repository.PasswordResetRepository;
import authentication.modules.user.repository.UserRepository;
import authentication.modules.user.service.impl.PasswordResetService;
import authentication.modules.user.service.impl.TokenService;
import authentication.modules.user.validation.PasswordResetValidator;
import authentication.modules.user.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetRepository tokenRepository;

    @Mock
    private MailService mailService;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordResetValidator passwordResetValidator;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private PasswordResetService passwordResetService;

    private User user;

    private PasswordResetToken passwordResetToken;

    private String token;

    @BeforeEach
    void setUp() {
        user = UserHelper.userOne();
        token = "test-token";
        passwordResetToken = new PasswordResetToken(token, user);
    }

    @Test
    void createPasswordResetTokenForUser_shouldSavePasswordResetToken() {
        passwordResetService.createPasswordResetTokenForUser(user, token);
        verify(tokenRepository).save(any(PasswordResetToken.class));
    }

    @Test
    void sendResetTokenEmail_shouldSendResetPasswordEmail() {
        when(templateEngine.process(anyString(), any())).thenReturn("ResetPassword");
        passwordResetService.sendResetTokenEmail(user.getEmail(), token);
        verify(mailService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void resetPassword_shouldResetPassword() {
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(passwordResetToken));
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        doNothing().when(passwordResetValidator).validateTokenExpiry(any(PasswordResetToken.class));

        passwordResetService.resetPassword(token, user.getPassword());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void resetPassword_shouldThrowNotFoundException_whenTokenIsInvalid() {
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());
        doThrow(new NotFoundException("Token not found")).when(passwordResetValidator)
                .validateResetToken(any(Optional.class));
        assertThrows(NotFoundException.class,
                () -> passwordResetService.resetPassword(token, user.getPassword()));
    }


    @Test
    void requestPasswordReset_shouldRequestPasswordReset() {
        when(tokenService.createToken()).thenReturn(token);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("Email Content");
        passwordResetService.requestPasswordReset(user.getEmail());
        verify(mailService).sendEmail(anyString(), anyString(), anyString());
    }
}

