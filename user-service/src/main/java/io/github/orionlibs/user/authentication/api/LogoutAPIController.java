package io.github.orionlibs.user.authentication.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.authentication.LogoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Tag(name = "User logout", description = "User manager")
public class LogoutAPIController extends WebService
{
    @Autowired private LogoutService logoutService;


    @Operation(
                    summary = "Logout user",
                    description = "Logout user",
                    responses = {@ApiResponse(responseCode = "200", description = "User logged out")}
    )
    @PostMapping(value = "/users/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response)
    {
        return ResponseEntity.ok(Map.of("token", logoutService.logoutUser(request, response, getUserID(request))));
    }
}
