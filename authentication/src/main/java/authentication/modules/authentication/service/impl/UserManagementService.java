package authentication.modules.authentication.service.impl;

import authentication.modules.authentication.dto.request.SignUpRequest;
import authentication.modules.user.model.User;
import authentication.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static authentication.modules.user.enums.ERole.USER;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(USER).build();

        return repository.save(user);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));
    }
}
