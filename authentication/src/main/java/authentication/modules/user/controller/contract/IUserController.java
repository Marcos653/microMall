package authentication.modules.user.controller.contract;

import authentication.modules.common.dto.PageRequest;
import authentication.modules.common.exception.NotFoundException;
import authentication.modules.user.dto.request.UserRequest;
import authentication.modules.user.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users", description = "User related operations")
public interface IUserController {

    @Operation(
            summary = "Get all users",
            description = "This endpoint retrieves all users in a paged format.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    @GetMapping
    Page<UserResponse> getAll(PageRequest pageable);

    @Operation(
            summary = "Get user by id",
            description = "This endpoint retrieves a user by id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/{id}")
    UserResponse getById(Long id) throws NotFoundException;

    @Operation(
            summary = "Register a new user",
            description = "This endpoint registers a new user.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Email already in use",
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping
    UserResponse register(UserRequest request);

    @Operation(
            summary = "Update an existing user",
            description = "This endpoint updates an existing user by id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PutMapping("/{id}")
    UserResponse update(Long id, UserRequest request) throws NotFoundException;

    @Operation(
            summary = "Delete a user",
            description = "This endpoint deletes a user by id.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(Long id) throws NotFoundException;
}
