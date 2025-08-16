package io.github.orionlibs.alarm.model;

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
public class AlarmEventDataDAOTest
{
    @Autowired AlarmEventDataDAO dao;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void findById()
    {
        AlarmEventDataModel model1 = new AlarmEventDataModel();
        model1.setPropertyName("prop1");
        model1.setPropertyValue("value1");
        model1 = dao.saveAndFlush(model1);
        AlarmEventDataModel model2 = new AlarmEventDataModel();
        model2.setPropertyName("prop2");
        model2.setPropertyValue("value2");
        model2 = dao.saveAndFlush(model2);
        Optional<AlarmEventDataModel> modelWrap = dao.findById(model1.getId());
        assertThat(modelWrap.get().getPropertyName()).isEqualTo("prop1");
        assertThat(modelWrap.get().getPropertyValue()).isEqualTo("value1");
    }


    @Test
    void findAll()
    {
        AlarmEventDataModel model1 = new AlarmEventDataModel();
        model1.setPropertyName("prop1");
        model1.setPropertyValue("value1");
        model1 = dao.saveAndFlush(model1);
        AlarmEventDataModel model2 = new AlarmEventDataModel();
        model2.setPropertyName("prop2");
        model2.setPropertyValue("value2");
        model2 = dao.saveAndFlush(model2);
        List<AlarmEventDataModel> models = dao.findAll();
        assertThat(models.get(0).getPropertyName()).isEqualTo("prop1");
        assertThat(models.get(0).getPropertyValue()).isEqualTo("value1");
        assertThat(models.get(1).getPropertyName()).isEqualTo("prop2");
        assertThat(models.get(1).getPropertyValue()).isEqualTo("value2");
    }
}
