package io.github.orionlibs.system.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.system.ControllerUtils;
import io.github.orionlibs.system.configuration.SystemConfigurationService;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import io.github.orionlibs.system.converter.ConfigurationModelToDTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "System", description = "Configuration")
public class GetConfigurationAPIController extends WebService
{
    @Autowired
    private SystemConfigurationService configurationService;
    @Autowired
    private ConfigurationModelToDTOConverter configurationModelToDTOConverter;


    @Operation(
                    summary = "Retrieves system configuration",
                    description = "Retrieves system configuration",
                    responses = {@ApiResponse(responseCode = "200", description = "System configuration found",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ConfigurationsDTO.class)
                                    ))}
    )
    @GetMapping(value = "/systems/configurations", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConfigurationsDTO> getAllSystemConfigurations()
    {
        List<ConfigurationModel> allConfiguration = configurationService.getAll();
        List<ConfigurationDTO> configurationsToReturn = allConfiguration.stream()
                        .map(config -> configurationModelToDTOConverter.convert(config))
                        .toList();
        return ResponseEntity.ok(new ConfigurationsDTO(configurationsToReturn));
    }
}
