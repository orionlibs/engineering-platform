package io.github.orionlibs.user.password.forgot.api;

import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.password.forgot.ForgotPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Tag(name = "Users", description = "User manager")
public class ValidateForgotPasswordRequestAPIController
{
    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @Operation(
                    summary = "Validate forgot password request",
                    description = "Validate forgot password request",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "forgotPasswordCode",
                                    description = "The code that is used to identify the password change request for a particular user",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "Forgot password request found"),
                                    @ApiResponse(responseCode = "404", description = "Invalid input")}
    )
    @GetMapping(value = "/users/passwords/forgot-requests/{forgotPasswordCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("!isAuthenticated()")
    public ResponseEntity<?> validateForgotPasswordRequest(@PathVariable String forgotPasswordCode)
    {
        if(forgotPasswordService.isCodeValid(forgotPasswordCode))
        {
            return ResponseEntity.ok(Map.of());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
