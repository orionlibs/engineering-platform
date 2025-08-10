package io.github.orionlibs.user.password.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.TestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
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
public class UpdatePasswordAPIServiceTest
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
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/passwords";
        dao.deleteAll();
        headers = new HttpHeaders();
        user = testUtils.registerUser("me@email.com", "USER");
    }


    @Test
    void updatePassword()
    {
        RestAssured.baseURI = basePath;
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                        .password("bunkzh3Z!1")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void updatePassword_userNotFound()
    {
        RestAssured.baseURI = basePath;
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                        .password("bunkzh3Z!1")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, UUID.randomUUID().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(401);
    }


    @Test
    void updatePassword_invalidPassword()
    {
        RestAssured.baseURI = basePath;
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                        .password("4528")
                        .build();
        Response response = apiUtils.makePatchAPICall(request, headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("Password does not meet security requirements");
    }
}
