package authentication.modules.authentication.controller.contract;

import authentication.modules.authentication.dto.request.SignInRequest;
import authentication.modules.authentication.dto.request.SignUpRequest;
import authentication.modules.authentication.dto.response.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "Authentication related operations")
public interface IAuthenticationController {

    @Operation(
            summary = "Register a new user",
            description = "This endpoint registers a new user and returns JWT token.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/signup")
    ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request);

    @Operation(
            summary = "Authenticate a user",
            description = "This endpoint authenticates a user and returns JWT token.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User authenticated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Invalid email or password"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/signin")
    ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request);

    @Operation(
            summary = "Authenticate a user with OAuth",
            description = "This endpoint authenticates a user with Firebase token and returns JWT token.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User authenticated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/oauth")
    ResponseEntity<JwtAuthenticationResponse> authenticateWithOAuth(@RequestBody String firebaseToken);
}
