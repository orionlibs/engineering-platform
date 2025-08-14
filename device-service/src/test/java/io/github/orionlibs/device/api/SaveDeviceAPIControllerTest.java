package io.github.orionlibs.device.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.UserAuthority;
import io.github.orionlibs.device.ControllerUtils;
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
class SaveDeviceAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    HttpHeaders headers;
    String userID;


    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        headers = new HttpHeaders();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/devices";
        userID = UUID.randomUUID().toString();
    }


    @Test
    void saveDevice_anonymous()
    {
        SaveDeviceRequest temp = SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build();
        Response response = apiUtils.makePostAPICall(temp, headers);
        assertThat(response.statusCode()).isEqualTo(403);
    }


    @Test
    void saveDevice()
    {
        SaveDeviceRequest temp = SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build();
        Response response = apiUtils.makePostAPICall(temp, headers, userID, UserAuthority.USER.name());
        assertThat(response.statusCode()).isEqualTo(201);
    }
}
