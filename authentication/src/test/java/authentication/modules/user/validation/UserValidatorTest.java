package authentication.modules.user.validation;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.common.exception.ValidationException;
import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.model.User;
import authentication.modules.user.repository.UserRepository;
import authentication.modules.user.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRequest userRequest;

    @Test
    void validateUserById_shouldThrowNotFoundException_whenUserIdIsInvalid() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userValidator.validateUserById(1L));
    }

    @Test
    void validateNewUser_shouldThrowValidationException_whenEmailIsInUse() {
        when(userRequest.getEmail()).thenReturn("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(ValidationException.class, () -> userValidator.validateNewUser(userRequest));
    }

    @Test
    void validateUserByEmail_shouldThrowNotFoundException_whenUserEmailIsInvalid() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userValidator.validateUserByEmail("invalid@example.com"));
    }
}
