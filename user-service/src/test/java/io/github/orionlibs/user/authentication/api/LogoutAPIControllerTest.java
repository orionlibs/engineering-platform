package io.github.orionlibs.user.authentication.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
import io.github.orionlibs.user.registration.UserRegistrationService;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LogoutAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired APITestUtils apiUtils;
    String basePath;
    HttpHeaders headers;
    @Autowired UserRegistrationService userRegistrationService;
    UserModel user;


    @BeforeEach
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/users/logout";
        dao.deleteAll();
        user = userRegistrationService.registerUser(UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority("USER")
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build());
    }


    @Test
    void logoutUser()
    {
        RestAssured.baseURI = basePath;
        Response response = apiUtils.makePostAPICall(null, headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        Map body = response.as(Map.class);
        assertThat(((String)body.get("token")).isEmpty()).isTrue();
    }
}
