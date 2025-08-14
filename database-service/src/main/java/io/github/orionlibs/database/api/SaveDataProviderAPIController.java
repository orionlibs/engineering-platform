package io.github.orionlibs.database.api;

import io.github.orionlibs.core.api.APIService;
import io.github.orionlibs.database.ControllerUtils;
import io.github.orionlibs.database.DatabaseService;
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
@Tag(name = "Data provider", description = "Data provider manager")
public class SaveDataProviderAPIController extends APIService
{
    @Autowired private DatabaseService databaseService;


    @Operation(
                    summary = "Save data provider",
                    description = "Save data provider",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = SaveDataProviderRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "201", description = "Data provider saved"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/databases", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DATABASE_MANAGER')")
    public ResponseEntity<?> saveDataProvider(@Valid @RequestBody SaveDataProviderRequest requestBean)
    {
        databaseService.saveDataProvider(requestBean);
        return ResponseEntity.created(null).body(Map.of());
    }
}
