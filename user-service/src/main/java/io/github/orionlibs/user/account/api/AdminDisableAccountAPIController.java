package io.github.orionlibs.user.account.api;

import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.user.ControllerUtils;
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
public class AdminDisableAccountAPIController
{
    @Autowired
    private UserService userService;


    @Operation(
                    summary = "Disable any user's account as administrator",
                    description = "Disable any user's account as administrator",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = AdminEnableAccountRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "User account disabled"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                                    @ApiResponse(responseCode = "404", description = "User not found")}
    )
    @PatchMapping(value = "/admin/users/disablements", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> adminDisableAccount(@Valid @RequestBody AdminEnableAccountRequest request)
    {
        boolean accountDisabled = userService.disable(request.getUserID());
        if(accountDisabled)
        {
            return ResponseEntity.ok(Map.of());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
