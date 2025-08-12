package io.github.orionlibs.user.setting.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.user.TestUtils;
import io.github.orionlibs.core.user.UserAuthority;
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
public class AdminUpdateUserSettingAPIServiceTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired UserSettingsService userSettingsService;
    @Autowired UserServiceAPITestUtils apiUtils;
    @Autowired TestUtils testUtils;
    String basePath;
    HttpHeaders headers;
    UserModel user;
    UserModel admin;


    @BeforeEach
    void setup()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/admin/users/%s/settings/%s";
        dao.deleteAll();
        headers = new HttpHeaders();
        user = testUtils.registerUser("me@email.com", "USER");
        admin = testUtils.registerUser("admin@email.com", UserAuthority.ADMINISTRATOR.name());
    }


    @Test
    void adminUpdateUserSetting()
    {
        List<UserSettingsModel> settings = userSettingsService.getByUserID(user.getId().toString());
        String settingID = settings.get(0).getId().toString();
        RestAssured.baseURI = String.format(basePath, user.getId().toString(), settingID);
        AdminUpdateUserSettingRequest request = AdminUpdateUserSettingRequest.builder()
                        .settingValue("light")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(200);
        settings = userSettingsService.getByUserID(user.getId().toString());
        assertThat(settings.get(0).getSettingValue()).isEqualTo("light");
    }
}
