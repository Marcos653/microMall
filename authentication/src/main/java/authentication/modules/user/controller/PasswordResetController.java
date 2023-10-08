package authentication.modules.user.controller;

import authentication.modules.user.controller.contract.IPasswordResetController;
import authentication.modules.user.dto.request.PasswordChangeRequest;
import authentication.modules.user.dto.request.PasswordResetRequest;
import authentication.modules.user.service.impl.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users-password")
public class PasswordResetController implements IPasswordResetController {

    private final PasswordResetService resetService;

    @Override
    @PostMapping("/password-reset")
    public ResponseEntity<Void> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        resetService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/password-change")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        resetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
