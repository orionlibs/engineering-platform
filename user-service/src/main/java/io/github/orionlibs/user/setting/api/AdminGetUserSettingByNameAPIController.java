package io.github.orionlibs.user.setting.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.setting.UserSettingsService;
import io.github.orionlibs.user.setting.converter.UserSettingModelToDTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
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
public class AdminGetUserSettingByNameAPIController extends WebService
{
    @Autowired private UserSettingsService userSettingsService;
    @Autowired private UserSettingModelToDTOConverter userSettingModelToDTOConverter;


    @Operation(
                    summary = "Get user setting by name of any user as administrator",
                    description = "Get user setting by name of any user as administrator",
                    parameters = {@io.swagger.v3.oas.annotations.Parameter(
                                    name = "userID",
                                    description = "The userID of the account of which to retrieve settings",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                                    @io.swagger.v3.oas.annotations.Parameter(
                                                    name = "settingName",
                                                    description = "The setting name to look for",
                                                    required = true,
                                                    in = ParameterIn.PATH,
                                                    schema = @Schema(type = "string")
                                    )},
                    responses = {@ApiResponse(responseCode = "200", description = "User account setting returned")}
    )
    @GetMapping(value = "/admin/users/{userID}/settings/names/{settingName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<UserSettingDTO> adminGetUserSettingByName(@PathVariable String userID, @PathVariable String settingName)
    {
        Optional<UserSettingsModel> userSetting = userSettingsService.getBySettingNameAndUserID(settingName, userID);
        return ResponseEntity.ok(userSettingModelToDTOConverter.convert(userSetting));
    }
}
