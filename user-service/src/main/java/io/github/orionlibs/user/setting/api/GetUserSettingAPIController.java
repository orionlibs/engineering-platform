package io.github.orionlibs.user.setting.api;

import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.setting.UserSettingsService;
import io.github.orionlibs.user.setting.converter.UserSettingModelToDTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Tag(name = "Users", description = "User manager")
public class GetUserSettingAPIController
{
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private UserSettingModelToDTOConverter userSettingModelToDTOConverter;


    @Operation(
                    summary = "Get user setting",
                    description = "Get user setting",
                    responses = {@ApiResponse(responseCode = "200", description = "User account setting returned")}
    )
    @GetMapping(value = "/users/settings/{settingsID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserSettingDTO> getUserSetting(@PathVariable String settingsID, @AuthenticationPrincipal Jwt jwt)
    {
        Optional<UserSettingsModel> userSetting = userSettingsService.getByIDAndUserID(settingsID, jwt.getSubject());
        return ResponseEntity.ok(userSettingModelToDTOConverter.convert(userSetting));
    }
}
