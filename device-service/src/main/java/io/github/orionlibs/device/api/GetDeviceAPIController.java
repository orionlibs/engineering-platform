package io.github.orionlibs.device.api;

import io.github.orionlibs.core.api.APIService;
import io.github.orionlibs.device.ControllerUtils;
import io.github.orionlibs.device.DeviceService;
import io.github.orionlibs.device.converter.DeviceModelToDTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerUtils.baseAPIPath)
@Tag(name = "Devices", description = "Devices")
public class GetDeviceAPIController extends APIService
{
    @Autowired private DeviceService deviceService;
    @Autowired private DeviceModelToDTOConverter deviceModelToDTOConverter;


    @Operation(
                    summary = "Retrieves device details",
                    description = "Retrieves device details",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "deviceID",
                                    description = "The ID of the device we want to find",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "Device details found",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = DeviceDTO.class)
                                    )),
                                    @ApiResponse(responseCode = "404", description = "Device not found")}
    )
    @GetMapping(value = "/devices/{deviceID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeviceDTO> getDeviceDetails(@PathVariable String deviceID)
    {
        return deviceService.getById(deviceID)
                        .map(deviceModelToDTOConverter::convert)
                        .filter(Objects::nonNull)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
