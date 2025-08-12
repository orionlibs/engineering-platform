package io.github.orionlibs.database.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.api.APIError;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.database.ControllerUtils;
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
class SaveDataProviderAPIControllerTest
{
    @LocalServerPort int port;
    @Autowired APITestUtils apiUtils;
    @Autowired UserDAO dao;
    String basePath;
    HttpHeaders headers;


    @BeforeEach
    public void setUp()
    {
        basePath = "http://localhost:" + port + ControllerUtils.baseAPIPath + "/databases";
    }


    @Test
    void saveDataProvider()
    {
        RestAssured.baseURI = basePath;
        SaveDataProviderRequest request = SaveDataProviderRequest.builder()
                        .databaseName("uns")
                        .connectionURL("jdbc:mysql://localhost:3306")
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers, UUID.randomUUID().toString(), "DATABASE_MANAGER");
        assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    void saveDataProvider_invalidDatabaseName()
    {
        RestAssured.baseURI = basePath;
        SaveDataProviderRequest request = SaveDataProviderRequest.builder()
                        .databaseName("")
                        .connectionURL("jdbc:mysql://localhost:3306")
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers, UUID.randomUUID().toString(), "DATABASE_MANAGER");
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("database_name must not be blank");
    }


    @Test
    void saveDataProvider_invalidConnectionURL()
    {
        RestAssured.baseURI = basePath;
        SaveDataProviderRequest request = SaveDataProviderRequest.builder()
                        .databaseName("uns")
                        .connectionURL("")
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .build();
        Response response = apiUtils.makePostAPICall(request, headers, UUID.randomUUID().toString(), "DATABASE_MANAGER");
        assertThat(response.statusCode()).isEqualTo(400);
        APIError body = response.as(APIError.class);
        assertThat(body.message()).isEqualTo("Validation failed for one or more fields");
        assertThat(body.fieldErrors().get(0).message()).isEqualTo("connectionURL must not be blank");
    }
}
