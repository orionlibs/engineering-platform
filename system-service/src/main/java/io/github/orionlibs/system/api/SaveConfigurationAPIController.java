package io.github.orionlibs.system.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.system.ControllerUtils;
import io.github.orionlibs.system.configuration.SystemConfigurationService;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Validated
@Tag(name = "System", description = "Configuration")
public class SaveConfigurationAPIController extends WebService
{
    @Autowired private SystemConfigurationService configurationService;


    @Operation(
                    summary = "Saves a system configuration",
                    description = "Saves a system configuration",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = SaveConfigurationRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "System configuration saved"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/systems/configurations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<?> updateSystemConfiguration(@Valid @RequestBody SaveConfigurationRequest requestBean)
    {
        ConfigurationModel model = configurationService.update(requestBean.getKey(), requestBean.getValue());
        return ResponseEntity.ok(Map.of());
        /*if(isDocumentFound)
        {
            return ResponseEntity.ok(Map.of());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }*/
    }
}
