package io.github.orionlibs.user.setting.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.user.TestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.core.user.setting.UserSettingsService;
import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.UserServiceAPITestUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GetUserSettingAPIServiceTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired UserSettingsService userSettingsService;
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
    void getUserSetting()
    {
        List<UserSettingsModel> settings = userSettingsService.getByUserID(user.getId().toString());
        String settingID = settings.get(0).getId().toString();
        RestAssured.baseURI = basePath + "/" + settingID;
        Response response = apiUtils.makeGetAPICall(headers, user.getId().toString(), "USER");
        UserSettingDTO body = response.as(UserSettingDTO.class);
        assertThat(body.settingName()).isEqualTo("theme");
        assertThat(body.settingValue()).isEqualTo("dark");
    }
}
