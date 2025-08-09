package io.github.orionlibs.database.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.tests.TestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.database.ControllerUtils;
import io.github.orionlibs.database.DatabaseService;
import io.github.orionlibs.database.model.DataProviderModel;
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
class GetNumberOfDataProvidersAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    @Autowired TestUtils testUtils;
    @Autowired UserDAO dao;
    @Autowired DatabaseService databaseService;
    String basePath;
    HttpHeaders headers;
    UserModel user;


    @BeforeEach
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/databases/count";
        dao.deleteAll();
        user = testUtils.registerUser("me@email.com", "USER");
        databaseService.deleteAll();
        DataProviderModel model = new DataProviderModel("jdbc:mysql://localhost1:3306", "me1@email.com", "bunkzh3Z!");
        model = databaseService.saveDataProvider(model);
        model = new DataProviderModel("jdbc:mysql://localhost2:3306", "me2@email.com", "bunkzh3Z!");
        model = databaseService.saveDataProvider(model);
    }


    @Test
    void getNumberOfDataProviders()
    {
        RestAssured.baseURI = basePath;
        Response response = apiUtils.makeGetAPICall(headers, user.getId().toString(), "USER");
        assertThat(response.statusCode()).isEqualTo(200);
        Map body = response.as(Map.class);
        assertThat(body.get("number_of_data_providers")).isEqualTo(2);
    }
}
