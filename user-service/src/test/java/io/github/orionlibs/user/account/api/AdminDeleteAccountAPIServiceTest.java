package io.github.orionlibs.user.account.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.user.UserAuthority;
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
public class AdminDeleteAccountAPIServiceTest
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
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/admin/users";
        dao.deleteAll();
        headers = new HttpHeaders();
        user = testUtils.registerUser("me@email.com", "USER");
        admin = testUtils.registerUser("admin@email.com", UserAuthority.ADMINISTRATOR.name());
    }


    @Test
    void adminDeleteAccount()
    {
        RestAssured.baseURI = basePath + "/" + user.getId().toString();
        Response response = apiUtils.makeDeleteAPICall(headers, admin.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(200);
    }
}
