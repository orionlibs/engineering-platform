package io.github.orionlibs.user.setting.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.setting.UserSettingsService;
import io.github.orionlibs.user.setting.converter.UserSettingModelToDTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
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
public class GetUserSettingsAPIController extends WebService
{
    @Autowired private UserSettingsService userSettingsService;
    @Autowired private UserSettingModelToDTOConverter userSettingModelToDTOConverter;


    @Operation(
                    summary = "Get user settings",
                    description = "Get user settings",
                    responses = {@ApiResponse(responseCode = "200", description = "User account settings returned")}
    )
    @GetMapping(value = "/users/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserSettingsDTO> getUserSettings(HttpServletRequest request)
    {
        List<UserSettingsModel> userSettings = userSettingsService.getByUserID(getUserID(request));
        List<UserSettingDTO> settings = new ArrayList<>();
        userSettings.forEach(m -> settings.add(userSettingModelToDTOConverter.convert(m)));
        return ResponseEntity.ok(new UserSettingsDTO(settings));
    }
}
