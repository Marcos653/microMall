package authentication.modules.user.validation;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.common.exception.ValidationException;
import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.model.User;
import authentication.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public User validateUserById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public void validateNewUser(UserRequest request) {
        repository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new ValidationException("This email is in use");
        });
    }

    public User validateUserByEmail(String email) throws NotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
