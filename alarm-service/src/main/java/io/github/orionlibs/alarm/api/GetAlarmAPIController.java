package io.github.orionlibs.alarm.api;

import io.github.orionlibs.alarm.AlarmService;
import io.github.orionlibs.alarm.ControllerUtils;
import io.github.orionlibs.alarm.converter.AlarmModelToDTOConverter;
import io.github.orionlibs.core.api.APIService;
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
@Tag(name = "Alarms", description = "Alarms")
public class GetAlarmAPIController extends APIService
{
    @Autowired private AlarmService alarmService;
    @Autowired private AlarmModelToDTOConverter alarmModelToDTOConverter;


    @Operation(
                    summary = "Retrieves alarm details",
                    description = "Retrieves alarm details",
                    parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "alarmID",
                                    description = "The ID of the alarm we want to find",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                    ),
                    responses = {@ApiResponse(responseCode = "200", description = "Alarm details found",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = AlarmDTO.class)
                                    )),
                                    @ApiResponse(responseCode = "404", description = "Alarm not found")}
    )
    @GetMapping(value = "/alarms/{alarmID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AlarmDTO> getAlarmDetails(@PathVariable String alarmID)
    {
        return alarmService.getByID(alarmID)
                        .map(alarmModelToDTOConverter::convert)
                        .filter(Objects::nonNull)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
