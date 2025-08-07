package io.github.orionlibs.user.password.forgot.api;

import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.password.ForgotPasswordService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "Users", description = "User manager")
public class ProcessForgotPasswordRequestAPIController
{
    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @Operation(
                    summary = "Process forgot password request",
                    description = "Process forgot password request",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "forgotPasswordCode",
                                    description = "The code that is used to identify the password change request for a particular user",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "Password request found"),
                                    @ApiResponse(responseCode = "404", description = "Invalid input")}
    )
    @PostMapping(value = "/users/passwords/forgot-requests/{forgotPasswordCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("!isAuthenticated()")
    public ResponseEntity<?> processForgotPasswordRequest(@PathVariable String forgotPasswordCode)
    {
        if(forgotPasswordService.isCodeValid(forgotPasswordCode))
        {
            boolean isSuccess = forgotPasswordService.processCode(forgotPasswordCode);
            return ResponseEntity.ok(Map.of());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
