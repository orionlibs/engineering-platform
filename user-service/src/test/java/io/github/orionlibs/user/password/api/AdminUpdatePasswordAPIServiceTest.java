package io.github.orionlibs.user.password.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.user.UserAuthority;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.TestUtils;
import io.github.orionlibs.user.UserServiceAPITestUtils;
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
public class AdminUpdatePasswordAPIServiceTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired UserServiceAPITestUtils apiUtils;
    @Autowired TestUtils testUtils;
    String basePath;
    HttpHeaders headers;
    UserModel user;
    UserModel admin;


    @BeforeEach
    void setup()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/admin/users/passwords";
        dao.deleteAll();
        headers = new HttpHeaders();
        user = testUtils.registerUser("me@email.com", "USER");
        admin = testUtils.registerUser("admin@email.com", UserAuthority.ADMINISTRATOR.name());
    }


    @Test
    void adminUpdateUserPassword()
    {
        RestAssured.baseURI = basePath;
        AdminUpdatePasswordRequest request = AdminUpdatePasswordRequest.builder()
                        .userID(user.getId().toString())
                        .password("bunkzh3Z!1")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void adminUpdatePassword_userNotFound()
    {
        RestAssured.baseURI = basePath;
        AdminUpdatePasswordRequest request = AdminUpdatePasswordRequest.builder()
                        .userID(UUID.randomUUID().toString())
                        .password("bunkzh3Z!1")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(404);
    }


    @Test
    void adminUpdatePassword_invalidPassword()
    {
        RestAssured.baseURI = basePath;
        AdminUpdatePasswordRequest request = AdminUpdatePasswordRequest.builder()
                        .userID(user.getId().toString())
                        .password("4528")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("Password does not meet security requirements");
    }


    @Test
    void adminUpdatePassword_invalidUserID()
    {
        RestAssured.baseURI = basePath;
        AdminUpdatePasswordRequest request = AdminUpdatePasswordRequest.builder()
                        .userID("invalidUUID")
                        .password("bunkzh3Z!")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("The value is not a valid UUID");
    }
}
