package io.github.orionlibs.user.account.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.account.AccountDetailsDTO;
import io.github.orionlibs.user.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Tag(name = "Users", description = "User manager")
public class GetAccountDetailsAPIController extends WebService
{
    @Autowired
    private AccountService accountService;


    @Operation(
                    summary = "Get user details",
                    description = "Get user details",
                    responses = {@ApiResponse(responseCode = "200", description = "User account details returned")}
    )
    @GetMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountDetailsDTO> getAccountDetails(HttpServletRequest request)
    {
        return ResponseEntity.ok(accountService.getDetailsByUserID(getUserID(request)));
    }
}
