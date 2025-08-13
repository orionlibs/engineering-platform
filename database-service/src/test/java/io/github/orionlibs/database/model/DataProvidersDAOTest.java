package io.github.orionlibs.database.model;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.database.model.DataProviderType.Type;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DataProvidersDAOTest
{
    @Autowired DataProvidersDAO dao;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void findById()
    {
        DataProviderModel model = new DataProviderModel("uns", Type.DATABASE, "jdbc:mysql://localhost:3306", "me@email.com", "bunkzh3Z!");
        model = dao.saveAndFlush(model);
        Optional<DataProviderModel> modelWrap = dao.findById(model.getId());
        assertThat(modelWrap.get().getName()).isEqualTo("uns");
        assertThat(modelWrap.get().getConnectionURL()).isEqualTo("jdbc:mysql://localhost:3306");
    }


    @Test
    void findAll()
    {
        DataProviderModel model1 = new DataProviderModel("uns1", Type.DATABASE, "jdbc:mysql://localhost:3306", "me@email.com", "bunkzh3Z!");
        model1 = dao.saveAndFlush(model1);
        DataProviderModel model2 = new DataProviderModel("uns2", Type.DATABASE, "jdbc:mysql://localhost:3306", "me@email.com", "bunkzh3Z!");
        model2 = dao.saveAndFlush(model2);
        List<DataProviderModel> models = dao.findAll();
        assertThat(models.get(0).getName()).isEqualTo("uns1");
        assertThat(models.get(0).getConnectionURL()).isEqualTo("jdbc:mysql://localhost:3306");
        assertThat(models.get(1).getName()).isEqualTo("uns2");
        assertThat(models.get(1).getConnectionURL()).isEqualTo("jdbc:mysql://localhost:3306");
    }
}
