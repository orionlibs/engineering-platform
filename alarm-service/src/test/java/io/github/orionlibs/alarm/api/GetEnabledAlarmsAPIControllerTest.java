package io.github.orionlibs.alarm.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.alarm.AlarmService;
import io.github.orionlibs.alarm.ControllerUtils;
import io.github.orionlibs.alarm.ValueConditionMode;
import io.github.orionlibs.alarm.model.AlarmModel;
import io.github.orionlibs.core.tests.APITestUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GetEnabledAlarmsAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    @Autowired AlarmService alarmService;
    String basePath;
    HttpHeaders headers;


    @BeforeEach
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/alarms/enabled";
        alarmService.deleteAll();
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
    }


    @Test
    void getEnabledAlarmsDetails()
    {
        RestAssured.baseURI = basePath;
        Response response = apiUtils.makeGetAPICall(headers, UUID.randomUUID().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        AlarmsDTO body = response.as(AlarmsDTO.class);
        assertThat(body.alarms().size()).isEqualTo(1);
    }
}
