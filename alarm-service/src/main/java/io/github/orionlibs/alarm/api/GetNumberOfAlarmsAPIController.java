package io.github.orionlibs.alarm.api;

import io.github.orionlibs.alarm.AlarmService;
import io.github.orionlibs.alarm.ControllerUtils;
import io.github.orionlibs.core.api.APIService;
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
@Tag(name = "Alarms", description = "Alarms")
public class GetNumberOfAlarmsAPIController extends APIService
{
    @Autowired private AlarmService alarmService;


    @Operation(
                    summary = "Retrieves number of alarms",
                    description = "Retrieves number of alarms",
                    responses = {@ApiResponse(responseCode = "200", description = "Number of alarms found")}
    )
    @GetMapping(value = "/alarms/count", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getNumberOfAlarms()
    {
        return ResponseEntity.ok(Map.of("number_of_alarms", alarmService.getNumberOfAlarms()));
    }
}
