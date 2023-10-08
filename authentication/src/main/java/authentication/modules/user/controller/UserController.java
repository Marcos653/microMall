package authentication.modules.user.controller;

import authentication.modules.common.dto.PageRequest;
import authentication.modules.common.exception.NotFoundException;
import authentication.modules.user.controller.contract.IUserController;
import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.dto.response.UserResponse;
import authentication.modules.user.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final UserService service;

    @Override
    public Page<UserResponse> getAll(PageRequest pageable) {
        return service.getAll(pageable);
    }

    @Override
    public UserResponse getById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }

    @Override
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        return service.register(request);
    }

    @Override
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) throws NotFoundException {
        return service.update(id, request);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) throws NotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
