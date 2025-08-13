package io.github.orionlibs.user.password.api;

import io.github.orionlibs.core.api.APIService;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.password.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "Users", description = "User manager")
public class AdminUpdatePasswordAPIController extends APIService
{
    @Autowired private PasswordService passwordService;


    @Operation(
                    summary = "Update any user's password as administrator",
                    description = "Update any user's password as administrator",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = AdminUpdatePasswordRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "201", description = "User password updated"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                                    @ApiResponse(responseCode = "404", description = "User not found")}
    )
    @PatchMapping(value = "/admin/users/passwords", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> adminUpdateUserPassword(@Valid @RequestBody AdminUpdatePasswordRequest request)
    {
        boolean passwordUpdated = passwordService.update(request);
        if(passwordUpdated)
        {
            return ResponseEntity.ok(Map.of());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
