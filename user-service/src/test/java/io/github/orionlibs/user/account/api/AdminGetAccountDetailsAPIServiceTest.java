package io.github.orionlibs.user.account.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.tests.TestUtils;
import io.github.orionlibs.core.user.AccountDetailsDTO;
import io.github.orionlibs.core.user.UserAuthority;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.ControllerUtils;
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
public class AdminGetAccountDetailsAPIServiceTest
{
    @LocalServerPort int port;
    @Autowired UserDAO dao;
    @Autowired APITestUtils apiUtils;
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
    void adminGetAccountDetails()
    {
        RestAssured.baseURI = basePath + "/" + user.getId().toString();
        Response response = apiUtils.makeGetAPICall(headers, user.getId().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(200);
        AccountDetailsDTO body = response.as(AccountDetailsDTO.class);
        assertThat(body.username()).isEqualTo("me@email.com");
    }


    @Test
    void adminGetAccountDetails_userNotFound()
    {
        RestAssured.baseURI = basePath + "/" + user.getId().toString();
        Response response = apiUtils.makeGetAPICall(headers, UUID.randomUUID().toString(), UserAuthority.ADMINISTRATOR.name());
        assertThat(response.statusCode()).isEqualTo(401);
    }
}
