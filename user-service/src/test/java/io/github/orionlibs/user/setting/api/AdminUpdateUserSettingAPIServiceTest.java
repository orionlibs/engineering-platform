package io.github.orionlibs.user.setting.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.registration.UserRegistrationService;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
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
public class AdminUpdateUserSettingAPIServiceTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired UserRegistrationService userRegistrationService;
    @Autowired APITestUtils apiUtils;
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
        user = userRegistrationService.registerUser(UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority("USER")
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build());
        admin = userRegistrationService.registerUser(UserRegistrationRequest.builder()
                        .username("admin@email.com")
                        .password("bunkzh3Z!")
                        .authority("ADMINISTRATOR")
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build());
    }


    @Test
    void adminUpdateUserSetting()
    {
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/settings";
        Response response = apiUtils.makeGetAPICall(headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        UserSettingsDTO body = response.as(UserSettingsDTO.class);
        String settingID = body.settings().get(0).id();
        RestAssured.baseURI = String.format(basePath, user.getId().toString(), settingID);
        AdminUpdateUserSettingRequest request = AdminUpdateUserSettingRequest.builder()
                        .settingValue("light")
                        .build();
        response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), "ADMINISTRATOR");
        assertThat(response.statusCode()).isEqualTo(200);
        RestAssured.baseURI = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/settings";
        response = apiUtils.makeGetAPICall(headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        body = response.as(UserSettingsDTO.class);
        assertThat(body.settings().get(0).settingValue()).isEqualTo("light");
    }
}
