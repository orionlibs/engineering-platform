package io.github.orionlibs.user.account.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.UserAuthority;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.registration.UserRegistrationService;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
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
public class AdminEnableAccountAPIServiceTest
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
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/admin/users/enablements";
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
                        .authority(UserAuthority.ADMINISTRATOR.name())
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build());
    }


    @Test
    void adminEnableAccount()
    {
        RestAssured.baseURI = basePath;
        AdminEnableAccountRequest request = AdminEnableAccountRequest.builder()
                        .userID(user.getId().toString())
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void adminEnableAccount_userNotFound()
    {
        RestAssured.baseURI = basePath;
        AdminEnableAccountRequest request = AdminEnableAccountRequest.builder()
                        .userID(UUID.randomUUID().toString())
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(404);
    }


    @Test
    void adminEnableAccount_invalidUserID()
    {
        RestAssured.baseURI = basePath;
        AdminEnableAccountRequest request = AdminEnableAccountRequest.builder()
                        .userID("invalidUUID")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("The value is not a valid UUID");
    }
}
