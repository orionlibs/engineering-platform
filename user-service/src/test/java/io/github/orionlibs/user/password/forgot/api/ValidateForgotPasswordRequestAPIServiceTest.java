package io.github.orionlibs.user.password.forgot.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.password.forgot.ForgotPasswordService;
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
public class ValidateForgotPasswordRequestAPIServiceTest
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
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/passwords/forgot-requests";
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
    void validateForgotPasswordRequest()
    {
        RestAssured.baseURI = basePath + "/" + requestBean.getForgotPasswordCodeGenerated();
        Response response = apiUtils.makeGetAPICall(headers);
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void createForgotPasswordRequest_forgotPasswordCodeNotFound()
    {
        RestAssured.baseURI = basePath + "/nonexistentcode";
        Response response = apiUtils.makeGetAPICall(headers);
        assertThat(response.statusCode()).isEqualTo(404);
    }


    @Test
    void createForgotPasswordRequest_authenticatedUser()
    {
        RestAssured.baseURI = basePath + "/" + requestBean.getForgotPasswordCodeGenerated();
        Response response = apiUtils.makeGetAPICall(headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(403);
    }
}
