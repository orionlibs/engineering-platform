package io.github.orionlibs.system.configuration.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ConfigurationDAOTest
{
    @Autowired ConfigurationDAO dao;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void findByKey()
    {
        ConfigurationModel model1 = new ConfigurationModel("key1", "value1");
        model1 = dao.saveAndFlush(model1);
        ConfigurationModel model2 = new ConfigurationModel("key2", "value2");
        model2 = dao.saveAndFlush(model2);
        Optional<ConfigurationModel> modelWrap = dao.findByKey("key1");
        assertThat(modelWrap.get().getKey()).isEqualTo("key1");
        assertThat(modelWrap.get().getValue()).isEqualTo("value1");
    }


    @Test
    void findAll()
    {
        ConfigurationModel model1 = new ConfigurationModel("key1", "value1");
        model1 = dao.saveAndFlush(model1);
        ConfigurationModel model2 = new ConfigurationModel("key2", "value2");
        model2 = dao.saveAndFlush(model2);
        List<ConfigurationModel> models = dao.findAll();
        assertThat(models.get(0).getKey()).isEqualTo("key1");
        assertThat(models.get(0).getValue()).isEqualTo("value1");
        assertThat(models.get(1).getKey()).isEqualTo("key2");
        assertThat(models.get(1).getValue()).isEqualTo("value2");
    }
}
