package io.github.orionlibs.database;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.database.model.DataProviderModel;
import io.github.orionlibs.database.model.DataProviderType.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseServiceTest
{
    @Autowired DatabaseService databaseService;


    @BeforeEach
    void setup()
    {
        databaseService.deleteAll();
    }


    @Test
    void saveDataProvider()
    {
        DataProviderModel model = new DataProviderModel("uns1", Type.DATABASE, "jdbc:mysql://localhost:3306", "me@email.com", "bunkzh3Z!");
        model = databaseService.saveDataProvider(model);
        assertThat(model.getId().toString().length()).isGreaterThan(10);
    }
}
