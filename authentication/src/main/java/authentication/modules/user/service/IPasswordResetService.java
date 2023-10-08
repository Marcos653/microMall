package authentication.modules.user.service;

import authentication.modules.user.model.User;

public interface IPasswordResetService {

    void createPasswordResetTokenForUser(User user, String token);

    void sendResetTokenEmail(String userEmail, String token);

    void resetPassword(String token, String newPassword);

    void requestPasswordReset(String email);
}
