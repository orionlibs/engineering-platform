package io.github.orionlibs.alarm.api;

import io.github.orionlibs.alarm.AlarmService;
import io.github.orionlibs.alarm.ControllerUtils;
import io.github.orionlibs.alarm.converter.AlarmModelToDTOConverter;
import io.github.orionlibs.alarm.model.AlarmModel;
import io.github.orionlibs.core.api.APIService;
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
@Tag(name = "Alarms", description = "Alarms")
public class GetDisabledAlarmsAPIController extends APIService
{
    @Autowired private AlarmService alarmService;
    @Autowired private AlarmModelToDTOConverter alarmModelToDTOConverter;


    @Operation(
                    summary = "Retrieves disabled alarm details",
                    description = "Retrieves disabled alarm details",
                    responses = {@ApiResponse(responseCode = "200", description = "Disabled alarms details found",
                                    content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = AlarmsDTO.class)
                                    ))}
    )
    @GetMapping(value = "/alarms/disabled", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AlarmsDTO> getDisabledAlarmsDetails()
    {
        List<AlarmModel> alarms = alarmService.getDisabledAlarms();
        List<AlarmDTO> alarmsToReturn = new ArrayList<>();
        for(AlarmModel model : alarms)
        {
            alarmsToReturn.add(alarmModelToDTOConverter.convert(model));
        }
        return ResponseEntity.ok(new AlarmsDTO(alarmsToReturn));
    }
}
