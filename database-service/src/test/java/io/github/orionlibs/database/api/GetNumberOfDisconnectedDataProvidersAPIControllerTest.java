package io.github.orionlibs.database.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.database.ControllerUtils;
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
class GetNumberOfDisconnectedDataProvidersAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    String basePath;
    HttpHeaders headers;


    @BeforeEach
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/databases/disconnected/count";
    }


    @Test
    void getNumberOfConnectedDataProviders()
    {
        RestAssured.baseURI = basePath;
        Response response = apiUtils.makeGetAPICall(headers, UUID.randomUUID().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        Map body = response.as(Map.class);
        assertThat(body.get("number_of_disconnected_data_providers")).isEqualTo(0);
    }
}
