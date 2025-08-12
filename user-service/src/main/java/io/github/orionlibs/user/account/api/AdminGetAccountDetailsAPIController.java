package io.github.orionlibs.user.account.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.account.AccountDetailsDTO;
import io.github.orionlibs.user.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
public class AdminGetAccountDetailsAPIController extends WebService
{
    @Autowired private AccountService accountService;


    @Operation(
                    summary = "Get any user's details as administrator",
                    description = "Get any user's details as administrator",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "userID",
                                    description = "The userID of the account of which the administrator wants details",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "User account details returned")}
    )
    @GetMapping(value = "/admin/users/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<AccountDetailsDTO> adminGetAccountDetails(@PathVariable String userID)
    {
        return ResponseEntity.ok(accountService.getDetailsByUserID(userID));
    }
}
