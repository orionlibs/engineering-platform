package io.github.orionlibs.database.api;

import io.github.orionlibs.core.api.WebService;
import io.github.orionlibs.database.ControllerUtils;
import io.github.orionlibs.database.DatabaseConnectivityService;
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
public class GetNumberOfConnectedDataProvidersAPIController extends WebService
{
    @Autowired
    private DatabaseConnectivityService databaseConnectivityService;


    @Operation(
                    summary = "Get number of connected data providers",
                    description = "Get number of connected data providers",
                    responses = {@ApiResponse(responseCode = "200", description = "Number of connected data providers found")}
    )
    @GetMapping(value = "/databases/connected/count", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getNumberOfConnectedDataProviders()
    {
        return ResponseEntity.ok(Map.of("number_of_connected_data_providers", databaseConnectivityService.getNumberOfConnectedDataProviders()));
    }
}
