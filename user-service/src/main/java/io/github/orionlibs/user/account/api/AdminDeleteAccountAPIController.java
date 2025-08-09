package io.github.orionlibs.user.account.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.user.ControllerUtils;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Tag(name = "Users", description = "User manager")
public class AdminDeleteAccountAPIController extends WebService
{
    @Autowired
    private UserService userService;


    @Operation(
                    summary = "Delete any user's account as administrator",
                    description = "Delete any user's account as administrator",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "userID",
                                    description = "The userID of the account to delete",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "User account deleted"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @DeleteMapping(value = "/admin/users/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> adminDeleteAccount(@PathVariable String userID)
    {
        userService.delete(userID);
        return ResponseEntity.ok(Map.of());
    }
}
