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
public class GetAlarmAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    HttpHeaders headers;
    @Autowired AlarmService alarmService;
    String baseURL;


    @BeforeEach
    public void setUp()
    {
        headers = new HttpHeaders();
        baseURL = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/alarms";
    }


    @Test
    void getAlarmDetails()
    {
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
        RestAssured.baseURI = baseURL + "/" + model1.getId().toString();
        Response response = apiUtils.makeGetAPICall(null, UUID.randomUUID().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        AlarmDTO body = response.as(AlarmDTO.class);
        assertThat(body.name()).isEqualTo("alarm1");
        assertThat(body.description()).isEqualTo("desc1");
        assertThat(body.tagID()).isEqualTo("tag-id-1");
        assertThat(body.isEnabled()).isFalse();
        assertThat(body.setpoint()).isEqualTo("setpoint1");
        assertThat(body.valueConditionMode()).isEqualTo("EQUALS");
        RestAssured.baseURI = baseURL + "/" + model2.getId().toString();
        response = apiUtils.makeGetAPICall(null, UUID.randomUUID().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        body = response.as(AlarmDTO.class);
        assertThat(body.name()).isEqualTo("alarm2");
        assertThat(body.description()).isEqualTo("desc2");
        assertThat(body.tagID()).isEqualTo("tag-id-2");
        assertThat(body.isEnabled()).isTrue();
        assertThat(body.setpoint()).isEqualTo("15.56");
        assertThat(body.valueConditionMode()).isEqualTo("GREATER_OR_EQUAL_TO_SETPOINT");
    }
}
