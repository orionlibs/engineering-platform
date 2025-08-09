package io.github.orionlibs.system.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.UserAuthority;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.core.user.registration.UserRegistrationService;
import io.github.orionlibs.core.user.registration.api.UserRegistrationRequest;
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
class SaveConfigurationAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    @Autowired UserDAO dao;
    @Autowired UserRegistrationService userRegistrationService;
    HttpHeaders headers;
    UserModel user;


    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp()
    {
        headers = new HttpHeaders();
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/systems/configurations";
        dao.deleteAll();
        user = userRegistrationService.registerUser(UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority("USER")
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build());
    }


    @Test
    void updateSystemConfigurations_invalid()
    {
        SaveConfigurationRequest config = SaveConfigurationRequest.builder()
                        .key("")
                        .value("US")
                        .build();
        Response response = apiUtils.makePostAPICall(config, headers, user.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(400);
    }


    @Test
    void updateSystemConfiguration_anonymous()
    {
        SaveConfigurationRequest config = SaveConfigurationRequest.builder()
                        .key("default.printing.timezone")
                        .value("US")
                        .build();
        Response response = apiUtils.makePostAPICall(config, headers);
        assertThat(response.statusCode()).isEqualTo(403);
    }


    @Test
    void updateSystemConfiguration()
    {
        SaveConfigurationRequest config = SaveConfigurationRequest.builder()
                        .key("default.printing.timezone")
                        .value("US")
                        .build();
        Response response = apiUtils.makePostAPICall(config, headers, user.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(200);
        response = apiUtils.makeGetAPICall(null, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        ConfigurationsDTO body = response.as(ConfigurationsDTO.class);
        assertThat(body.configurations().size()).isEqualTo(2);
        assertThat(body.configurations().contains(new ConfigurationDTO("default.printing.timezone", "US"))).isTrue();
    }
}
