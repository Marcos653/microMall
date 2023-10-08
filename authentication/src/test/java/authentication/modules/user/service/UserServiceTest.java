package authentication.modules.user.service;

import static authentication.modules.user.helpers.UserRequestHelper.userRequestOne;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.common.exception.ValidationException;
import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.dto.response.UserResponse;
import authentication.modules.user.helpers.UserHelper;
import authentication.modules.user.model.User;
import authentication.modules.user.repository.UserRepository;
import authentication.modules.user.service.impl.UserService;
import authentication.modules.user.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserService userService;

    private User user;

    private UserRequest request;

    private static final String NON_EXISTENT_EMAIL = "non.existent.user@example.com";

    @BeforeEach
    void setUp() {
        user = UserHelper.userOne();
        request = userRequestOne();
    }

    @Test
    void getAll_shouldReturnAllUsers_whenRequired() {
        when(repository.findAll(any(Pageable.class))).thenReturn(UserHelper.usersPage());

        var result = userService.getAll(PageRequest.of(0, 10));

        assertEquals(3, result.getNumberOfElements());
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    void getAll_shouldReturnEmpty_whenThereNoUsers() {
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        var result = userService.getAll(PageRequest.of(0, 10));

        assertEquals(0, result.getNumberOfElements());
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    void getUserById_shouldReturnUser_whenRequired() throws NotFoundException {
        when(userValidator.validateUserById(any(Long.class))).thenReturn(user);

        var result = userService.getById(1L);

        assertEquals(UserResponse.of(user), result);
        verify(userValidator).validateUserById(1L);
    }

    @Test
    void getUserById_shouldThrowNotFoundException_whenUserDoesNotExist() {
        when(userValidator.validateUserById(any(Long.class)))
                .thenThrow(new NotFoundException("User not found"));

        var exception = assertThrows(NotFoundException.class, () -> userService.getById(9999L));

        assertEquals("User not found", exception.getMessage());
        verify(userValidator).validateUserById(9999L);
    }

    @Test
    void register_shouldRegisterUser_whenRequired() {
        when(repository.save(any(User.class))).thenReturn(user);
        doNothing().when(userValidator).validateNewUser(request);

        var result = userService.register(request);

        assertEquals(UserResponse.of(user), result);
        verify(userValidator).validateNewUser(request);
        verify(repository).save(any(User.class));
    }

    @Test
    void register_shouldThrowValidationException_whenEmailInUse() {
        doThrow(new ValidationException("This email is in use")).when(userValidator).validateNewUser(request);

        assertThrows(ValidationException.class, () -> userService.register(request));
        verify(userValidator).validateNewUser(request);
    }

    @Test
    void update_shouldUpdateUser_whenRequired() throws NotFoundException {
        when(userValidator.validateUserById(1L)).thenReturn(user);
        doNothing().when(userValidator).validateNewUser(request);

        var userCaptor = ArgumentCaptor.forClass(User.class);
        when(repository.save(userCaptor.capture())).thenReturn(user);

        var result = userService.update(1L, request);

        assertEquals(UserResponse.of(user), result);

        var savedUser = userCaptor.getValue();
        assertEquals(request.getFirstName(), savedUser.getFirstName());
        assertEquals(request.getLastName(), savedUser.getLastName());
        assertEquals(request.getEmail(), savedUser.getEmail());

        verify(userValidator).validateUserById(1L);
        verify(userValidator).validateNewUser(request);
        verify(repository).save(any(User.class));
    }

    @Test
    void update_shouldThrowNotFoundException_whenUserDoesNotExist() {
        when(userValidator.validateUserById(any(Long.class)))
                .thenThrow(new NotFoundException("User not found"));

        var exception = assertThrows(NotFoundException.class, () -> userService.update(9999L, request));

        assertEquals("User not found", exception.getMessage());
        verify(userValidator).validateUserById(9999L);
    }

    @Test
    void update_shouldThrowValidationException_whenEmailInUse() {
        doThrow(new ValidationException("This email is in use")).when(userValidator).validateNewUser(request);

        assertThrows(ValidationException.class, () -> userService.update(1L, request));
        verify(userValidator).validateNewUser(request);
    }

    @Test
    void delete_shouldDeleteUser_whenUserExists() {
        when(userValidator.validateUserById(1L)).thenReturn(user);

        userService.delete(1L);

        verify(userValidator).validateUserById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowNotFoundException_whenUserDoesNotExist() {
        when(userValidator.validateUserById(any(Long.class)))
                .thenThrow(new NotFoundException("User not found"));

        var exception = assertThrows(NotFoundException.class, () -> userService.delete(9999L));

        assertEquals("User not found", exception.getMessage());
        verify(userValidator).validateUserById(9999L);
    }

    @Test
    void userDetailsService_shouldReturnUser_whenUsernameExists() {
        when(repository.findByEmail("test.user@example.com")).thenReturn(Optional.of(user));

        var userDetails = userService.userDetailsService().loadUserByUsername("test.user@example.com");

        assertEquals(user.getEmail(), userDetails.getUsername());
        verify(repository).findByEmail("test.user@example.com");
    }

    @Test
    void loadUserByUsername_ShouldLogWarning_WhenUserDoesNotExist() {
        when(repository.findByEmail(NON_EXISTENT_EMAIL)).thenReturn(Optional.empty());

        var result = userService.userDetailsService().loadUserByUsername(NON_EXISTENT_EMAIL);

        assertNull(result);
        verify(repository).findByEmail(NON_EXISTENT_EMAIL);
    }

}
