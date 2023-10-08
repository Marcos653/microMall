package authentication.modules.user.service.impl;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.dto.response.UserResponse;
import authentication.modules.user.model.User;
import authentication.modules.user.repository.UserRepository;
import authentication.modules.user.service.IUserService;
import authentication.modules.user.validation.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository repository;
    private final UserValidator userValidator;

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(UserResponse::of);
    }

    @Override
    public UserResponse getById(Long id) throws NotFoundException {
        return UserResponse.of(userValidator.validateUserById(id));
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest request) {
        userValidator.validateNewUser(request);
        var user = User.of(request);
        return UserResponse.of(repository.save(user));
    }

    @Override
    public UserResponse update(Long id, UserRequest request) throws NotFoundException {
        var user = userValidator.validateUserById(id);
        userValidator.validateNewUser(request);

        copyProperties(request, user, "id");
        return UserResponse.of(repository.save(user));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        userValidator.validateUserById(id);
        repository.deleteById(id);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email -> {
            var userOptional = repository.findByEmail(email);
            if (userOptional.isEmpty()) {
                log.warn("User with email {} not found.", email);
                return null;
            }
            return userOptional.get();
        };
    }
}
