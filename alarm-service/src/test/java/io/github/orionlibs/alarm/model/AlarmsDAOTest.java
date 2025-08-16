package io.github.orionlibs.alarm.model;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.alarm.ValueConditionMode;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AlarmsDAOTest
{
    @Autowired AlarmsDAO dao;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void findById()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = dao.saveAndFlush(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = dao.saveAndFlush(model2);
        Optional<AlarmModel> modelWrap = dao.findById(model2.getId());
        assertThat(modelWrap.get().getName()).isEqualTo("alarm2");
        assertThat(modelWrap.get().getDescription()).isEqualTo("desc2");
        assertThat(modelWrap.get().isEnabled()).isTrue();
        assertThat(modelWrap.get().getStringSetpoint()).isNull();
        assertThat(modelWrap.get().getNumericalSetpoint()).isEqualTo(15.56d);
        assertThat(modelWrap.get().getValueConditionMode()).isEqualTo(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
    }


    @Test
    void findAll()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = dao.saveAndFlush(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = dao.saveAndFlush(model2);
        List<AlarmModel> models = dao.findAll();
        assertThat(models.get(0).getName()).isEqualTo("alarm1");
        assertThat(models.get(0).getDescription()).isEqualTo("desc1");
        assertThat(models.get(0).isEnabled()).isFalse();
        assertThat(models.get(0).getStringSetpoint()).isEqualTo("setpoint1");
        assertThat(models.get(0).getNumericalSetpoint()).isNull();
        assertThat(models.get(0).getValueConditionMode()).isEqualTo(ValueConditionMode.EQUALS.getAsInt());
        assertThat(models.get(1).getName()).isEqualTo("alarm2");
        assertThat(models.get(1).getDescription()).isEqualTo("desc2");
        assertThat(models.get(1).isEnabled()).isTrue();
        assertThat(models.get(1).getStringSetpoint()).isNull();
        assertThat(models.get(1).getNumericalSetpoint()).isEqualTo(15.56d);
        assertThat(models.get(1).getValueConditionMode()).isEqualTo(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
    }


    @Test
    void findByTagIDAndIsEnabledTrue()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = dao.saveAndFlush(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = dao.saveAndFlush(model2);
        List<AlarmModel> models = dao.findByTagIDAndIsEnabledTrue("tag-id-2");
        assertThat(models.size()).isEqualTo(1);
        assertThat(models.get(0).getName()).isEqualTo("alarm2");
        assertThat(models.get(0).getDescription()).isEqualTo("desc2");
        assertThat(models.get(0).isEnabled()).isTrue();
        assertThat(models.get(0).getStringSetpoint()).isNull();
        assertThat(models.get(0).getNumericalSetpoint()).isEqualTo(15.56d);
        assertThat(models.get(0).getValueConditionMode()).isEqualTo(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
    }
}
