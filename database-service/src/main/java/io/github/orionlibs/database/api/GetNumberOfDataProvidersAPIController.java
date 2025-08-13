package io.github.orionlibs.database.api;

import io.github.orionlibs.core.api.APIService;
import io.github.orionlibs.database.ControllerUtils;
import io.github.orionlibs.database.DatabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
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
public class GetNumberOfDataProvidersAPIController extends APIService
{
    @Autowired private DatabaseService databaseService;


    @Operation(
                    summary = "Get number of data providers",
                    description = "Get number of data providers",
                    responses = {@ApiResponse(responseCode = "200", description = "Number of data providers found")}
    )
    @GetMapping(value = "/databases/count", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getNumberOfDataProviders()
    {
        return ResponseEntity.ok(Map.of("number_of_data_providers", databaseService.getNumberOfRegisteredDataProviders()));
    }
}
