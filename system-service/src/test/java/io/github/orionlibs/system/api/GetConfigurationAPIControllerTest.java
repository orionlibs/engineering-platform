package io.github.orionlibs.system.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.system.ControllerUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GetConfigurationAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    HttpHeaders headers;


    @BeforeEach
    public void setUp()
    {
        headers = new HttpHeaders();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/systems/configurations";
    }


    @Test
    void getAllSystemConfigurations()
    {
        Response response = apiUtils.makeGetAPICall(null, "Jimmy", "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        ConfigurationsDTO body = response.as(ConfigurationsDTO.class);
        assertThat(body.configurations().size()).isEqualTo(2);
        assertThat(body.configurations().contains(new ConfigurationDTO("default.printing.timezone", "GB"))).isTrue();
        assertThat(body.configurations().contains(new ConfigurationDTO("default.page.size", "50"))).isTrue();
    }
}
