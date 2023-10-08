package authentication.modules.user.controller.contract;

import authentication.modules.user.dto.request.PasswordChangeRequest;
import authentication.modules.user.dto.request.PasswordResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Password Reset", description = "Operations related to password reset")
public interface IPasswordResetController {

    @Operation(
            summary = "Request password reset",
            description = "This endpoint initiates a password reset request for a user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password reset request initiated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    ResponseEntity<Void> requestPasswordReset(PasswordResetRequest request);

    @Operation(
            summary = "Change password",
            description = "This endpoint allows a user to change their password using a valid reset token.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password changed successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid password reset token or email"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Password reset token not found"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Password reset token expired"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    ResponseEntity<Void> changePassword(PasswordChangeRequest request);
}
