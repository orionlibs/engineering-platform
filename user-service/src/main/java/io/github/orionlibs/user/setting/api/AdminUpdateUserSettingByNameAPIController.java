package io.github.orionlibs.user.setting.api;

import io.github.orionlibs.core.api.APIService;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.setting.UserSettingsService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "Users", description = "User manager")
public class AdminUpdateUserSettingByNameAPIController extends APIService
{
    @Autowired private UserSettingsService userSettingsService;


    @Operation(
                    summary = "Update one user setting of any user as administrator",
                    description = "Update one user setting of any user as administrator",
                    parameters = {@io.swagger.v3.oas.annotations.Parameter(
                                    name = "userID",
                                    description = "The ID of user whose setting to update",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                                    @io.swagger.v3.oas.annotations.Parameter(
                                                    name = "settingName",
                                                    description = "The name of the setting to update",
                                                    required = true,
                                                    in = ParameterIn.PATH,
                                                    schema = @Schema(type = "string")
                                    )},
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = UpdateUserSettingByNameRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "User account enabled"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                                    @ApiResponse(responseCode = "404", description = "User not found")}
    )
    @PatchMapping(value = "/admin/users/{userID}/settings/names/{settingName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> adminUpdateUserSettingByName(@PathVariable String userID, @PathVariable String settingName, @Valid @RequestBody UpdateUserSettingByNameRequest request)
    {
        userSettingsService.updateByName(settingName, request.getSettingValue(), userID);
        return ResponseEntity.ok(Map.of());
    }
}
