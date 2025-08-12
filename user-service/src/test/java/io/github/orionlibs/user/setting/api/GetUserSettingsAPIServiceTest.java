package io.github.orionlibs.user.setting.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.user.TestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.UserServiceAPITestUtils;
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
public class GetUserSettingsAPIServiceTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired UserServiceAPITestUtils apiUtils;
    @Autowired TestUtils testUtils;
    String basePath;
    HttpHeaders headers;
    UserModel user;


    @BeforeEach
    void setup()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/settings";
        dao.deleteAll();
        headers = new HttpHeaders();
        user = testUtils.registerUser("me@email.com", "USER");
    }


    @Test
    void getUserSettings()
    {
        RestAssured.baseURI = basePath;
        Response response = apiUtils.makeGetAPICall(headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        UserSettingsDTO body = response.as(UserSettingsDTO.class);
        assertThat(body.settings().size()).isEqualTo(1);
        assertThat(body.settings().get(0).settingName()).isEqualTo("theme");
        assertThat(body.settings().get(0).settingValue()).isEqualTo("dark");
    }
}
