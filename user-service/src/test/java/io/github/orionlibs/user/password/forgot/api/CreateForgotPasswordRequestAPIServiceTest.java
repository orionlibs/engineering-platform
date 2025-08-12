package io.github.orionlibs.user.password.forgot.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.TestUtils;
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
public class CreateForgotPasswordRequestAPIServiceTest
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
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/passwords/forgot-requests";
        dao.deleteAll();
        headers = new HttpHeaders();
        user = testUtils.registerUser("me@email.com", "USER");
    }


    @Test
    void createForgotPasswordRequest()
    {
        RestAssured.baseURI = basePath;
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me@email.com")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void createForgotPasswordRequest_userNotFound()
    {
        RestAssured.baseURI = basePath;
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me111111@email.com")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void createForgotPasswordRequest_invalidUsername()
    {
        RestAssured.baseURI = basePath;
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("Invalid email address format");
    }


    @Test
    void createForgotPasswordRequest_authenticatedUser()
    {
        RestAssured.baseURI = basePath;
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me@email.com")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(403);
    }
}
