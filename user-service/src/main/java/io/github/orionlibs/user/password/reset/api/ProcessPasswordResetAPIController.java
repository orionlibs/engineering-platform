package io.github.orionlibs.user.password.reset.api;

import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.password.PasswordService;
import io.github.orionlibs.user.password.api.UpdatePasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "Users", description = "User manager")
public class ProcessPasswordResetAPIController
{
    @Autowired
    private PasswordService passwordService;


    @Operation(
                    summary = "Process reset password request",
                    description = "Process reset password request",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "forgotPasswordCode",
                                    description = "The code that is used to identify the password change request for a particular user",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = UpdatePasswordRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "Password change succeeded"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/users/passwords/resets/{forgotPasswordCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("!isAuthenticated()")
    public ResponseEntity<?> processResetPasswordRequestAfterForgotPasswordRequest(@PathVariable String forgotPasswordCode, @Valid @RequestBody UpdatePasswordRequest request)
    {
        if(passwordService.update(request, forgotPasswordCode))
        {
            return ResponseEntity.ok(Map.of());
        }
        else
        {
            return ResponseEntity.badRequest().build();
        }
    }
}
