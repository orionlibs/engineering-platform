package io.github.orionlibs.device.api;

import io.github.orionlibs.core.api.APIService;
import io.github.orionlibs.device.ControllerUtils;
import io.github.orionlibs.device.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Devices", description = "Devices")
public class GetNumberOfDevicesAPIController extends APIService
{
    @Autowired private DeviceService deviceService;


    @Operation(
                    summary = "Retrieves number of devices",
                    description = "Retrieves number of devices",
                    responses = {@ApiResponse(responseCode = "200", description = "Number of devices found",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = DeviceDTO.class)
                                    ))}
    )
    @GetMapping(value = "/devices/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Long>> getNumberOfDevices()
    {
        return ResponseEntity.ok(Map.of("number_of_devices", deviceService.getNumberOfDevices()));
    }
}
