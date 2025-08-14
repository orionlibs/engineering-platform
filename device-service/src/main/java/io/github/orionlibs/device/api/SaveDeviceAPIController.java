package io.github.orionlibs.device.api;

import io.github.orionlibs.core.api.APIService;
import io.github.orionlibs.device.ControllerUtils;
import io.github.orionlibs.device.DeviceService;
import io.github.orionlibs.device.model.DeviceModel;
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
@Tag(name = "Devices", description = "Devices")
public class SaveDeviceAPIController extends APIService
{
    @Autowired private DeviceService deviceService;


    @Operation(
                    summary = "Saves a device",
                    description = "Saves a device",
                    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    content = @Content(
                                                    schema = @Schema(implementation = SaveDeviceRequest.class)
                                    )
                    ),
                    responses = {@ApiResponse(responseCode = "201", description = "System configuration saved"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input")}
    )
    @PostMapping(value = "/devices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> saveDevice(@Valid @RequestBody SaveDeviceRequest requestBean)
    {
        DeviceModel model = deviceService.save(requestBean);
        return ResponseEntity.created(null).body(Map.of());
    }
}
