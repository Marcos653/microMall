package authentication.modules.user.service.impl;

import authentication.modules.email.service.MailService;
import authentication.modules.user.model.PasswordResetToken;
import authentication.modules.user.model.User;
import authentication.modules.user.repository.PasswordResetRepository;
import authentication.modules.user.repository.UserRepository;
import authentication.modules.user.service.IPasswordResetService;
import authentication.modules.user.validation.PasswordResetValidator;
import authentication.modules.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class PasswordResetService implements IPasswordResetService {

    private final UserRepository userRepository;

    private final PasswordResetRepository tokenRepository;

    private final MailService mailService;

    private final SpringTemplateEngine templateEngine;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final PasswordResetValidator passwordResetValidator;

    private final UserValidator userValidator;

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        var myToken = new PasswordResetToken(token, user);
        myToken.setExpiryDate(tokenService.calculateExpiryDate());
        tokenRepository.save(myToken);
    }

    @Override
    public void sendResetTokenEmail(String userEmail, String token) {
        var context = new Context();
        context.setVariable("token", token);

        var emailContent = templateEngine.process("ResetPassword", context);

        mailService.sendEmail(userEmail, "Password Reset", emailContent);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        var resetTokenOptional = tokenRepository.findByToken(token);

        passwordResetValidator.validateResetToken(resetTokenOptional);

        var resetToken = resetTokenOptional.get();

        passwordResetValidator.validateTokenExpiry(resetToken);

        var user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void requestPasswordReset(String userEmail) {
        var user = userValidator.validateUserByEmail(userEmail);
        var token = tokenService.createToken();

        createPasswordResetTokenForUser(user, token);
        sendResetTokenEmail(userEmail, token);
    }
}
