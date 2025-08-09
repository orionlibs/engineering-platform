package io.github.orionlibs.database.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.database.ControllerUtils;
import io.github.orionlibs.database.DatabaseService;
import io.github.orionlibs.database.converter.DataProviderDTOToModelConverter;
import io.github.orionlibs.database.model.DataProviderModel;
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
public class SaveDataProviderAPIController extends WebService
{
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private DataProviderDTOToModelConverter dataProviderDTOToModelConverter;


    @Operation(
                    summary = "Save data provider",
                    description = "Save data provider",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = SaveDataProviderRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "Data provider saved"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/databases", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DATABASE_MANAGER')")
    public ResponseEntity<?> saveDataProvider(@Valid @RequestBody SaveDataProviderRequest requestBean)
    {
        DataProviderModel model = dataProviderDTOToModelConverter.convert(requestBean);
        databaseService.saveDataProvider(model);
        return ResponseEntity.ok(Map.of());
    }
}
