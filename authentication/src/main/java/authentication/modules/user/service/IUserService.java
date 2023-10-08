package authentication.modules.user.service;

import authentication.modules.common.exception.NotFoundException;
import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.dto.response.UserResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {

    Page<UserResponse> getAll(Pageable pageable);

    UserResponse getById(Long id) throws NotFoundException;

    UserResponse register(UserRequest request);

    UserResponse update(Long id, UserRequest request) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    UserDetailsService userDetailsService();
}
