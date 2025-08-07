package io.github.orionlibs.user.password.reset.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.password.api.UpdatePasswordRequest;
import io.github.orionlibs.user.password.forgot.ForgotPasswordService;
import io.github.orionlibs.user.password.forgot.api.CreateForgotPasswordRequestRequest;
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
public class ProcessPasswordResetAPIServiceTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired UserRegistrationService userRegistrationService;
    @Autowired ForgotPasswordService forgotPasswordService;
    @Autowired APITestUtils apiUtils;
    String basePath;
    HttpHeaders headers;
    UserModel user;
    CreateForgotPasswordRequestRequest requestBean;


    @BeforeEach
    void setup()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/passwords/resets";
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
        requestBean = CreateForgotPasswordRequestRequest.builder()
                        .username("me@email.com")
                        .build();
        boolean result = forgotPasswordService.process(requestBean);
    }


    @Test
    void processResetPasswordRequestAfterForgotPasswordRequest()
    {
        RestAssured.baseURI = basePath + "/" + requestBean.getForgotPasswordCodeGenerated();
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                        .password("bunkzh3Z!1")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void updatePassword_invalidPassword()
    {
        RestAssured.baseURI = basePath + "/" + requestBean.getForgotPasswordCodeGenerated();
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                        .password("4528")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers);
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("Password does not meet security requirements");
    }
}
