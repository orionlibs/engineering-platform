package io.github.orionlibs.database.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.database.ControllerUtils;
import io.github.orionlibs.database.DatabaseService;
import io.github.orionlibs.database.converter.DataProviderModelToDTOConverter;
import io.github.orionlibs.database.model.DataProviderModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Data provider", description = "Data provider manager")
public class GetDataProvidersAPIController extends WebService
{
    @Autowired private DatabaseService databaseService;
    @Autowired private DataProviderModelToDTOConverter dataProviderModelToDTOConverter;


    @Operation(
                    summary = "Get data providers",
                    description = "Get data providers",
                    responses = {@ApiResponse(responseCode = "200", description = "Data providers found",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = DataProvidersDetailsDTO.class)
                                    ))}
    )
    @GetMapping(value = "/databases", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DataProvidersDetailsDTO> getDataProvidersDetails()
    {
        List<DataProviderModel> databases = databaseService.getDataProviders();
        List<DataProviderDetailsDTO> databasesToReturn = new ArrayList<>();
        for(DataProviderModel model : databases)
        {
            databasesToReturn.add(dataProviderModelToDTOConverter.convert(model));
        }
        return ResponseEntity.ok(new DataProvidersDetailsDTO(databasesToReturn));
    }
}
