package io.github.orionlibs.device.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.device.ControllerUtils;
import io.github.orionlibs.device.DeviceService;
import io.github.orionlibs.device.model.DeviceModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;
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
public class GetNumberOfDevicesAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    HttpHeaders headers;
    @Autowired DeviceService deviceService;


    @BeforeEach
    public void setUp()
    {
        deviceService.deleteAll();
        headers = new HttpHeaders();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/devices/count";
    }


    @Test
    void getNumberOfDevices()
    {
        DeviceModel model1 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build());
        DeviceModel model2 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device2")
                        .deviceDescription("desc2")
                        .build());
        Response response = apiUtils.makeGetAPICall(null, UUID.randomUUID().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        Map body = response.as(Map.class);
        assertThat(body.get("number_of_devices")).isEqualTo(2);
    }
}
