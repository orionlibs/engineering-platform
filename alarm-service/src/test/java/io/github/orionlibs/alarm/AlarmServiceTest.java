package io.github.orionlibs.alarm;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.alarm.model.AlarmModel;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AlarmServiceTest
{
    @Autowired AlarmService alarmService;


    @BeforeEach
    void setup()
    {
        alarmService.deleteAll();
    }


    @Test
    void getByID()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm1");
        model2.setDescription("desc1");
        model2.setEnabled(false);
        model2.setStringSetpoint("setpoint1");
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
        Optional<AlarmModel> modelWrap = alarmService.getByID(model1.getId().toString());
        assertThat(modelWrap.get().getName()).isEqualTo("alarm1");
    }


    @Test
    void getEnabledAlarmsByTagID()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
        List<AlarmModel> models = alarmService.getEnabledAlarmsByTagID("tag-id-2");
        assertThat(models.size()).isEqualTo(1);
        assertThat(models.get(0).getName()).isEqualTo("alarm2");
        assertThat(models.get(0).getDescription()).isEqualTo("desc2");
        assertThat(models.get(0).isEnabled()).isTrue();
        assertThat(models.get(0).getStringSetpoint()).isNull();
        assertThat(models.get(0).getNumericalSetpoint()).isEqualTo(15.56d);
        assertThat(models.get(0).getValueConditionMode()).isEqualTo(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
    }


    @Test
    void getEnabledAlarms()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
        List<AlarmModel> models = alarmService.getEnabledAlarms();
        assertThat(models.size()).isEqualTo(1);
        assertThat(models.get(0).getName()).isEqualTo("alarm2");
        assertThat(models.get(0).getDescription()).isEqualTo("desc2");
        assertThat(models.get(0).isEnabled()).isTrue();
        assertThat(models.get(0).getStringSetpoint()).isNull();
        assertThat(models.get(0).getNumericalSetpoint()).isEqualTo(15.56d);
        assertThat(models.get(0).getValueConditionMode()).isEqualTo(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
    }


    @Test
    void getDisabledAlarms()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
        List<AlarmModel> models = alarmService.getDisabledAlarms();
        assertThat(models.size()).isEqualTo(1);
        assertThat(models.get(0).getName()).isEqualTo("alarm1");
        assertThat(models.get(0).getDescription()).isEqualTo("desc1");
        assertThat(models.get(0).isEnabled()).isFalse();
        assertThat(models.get(0).getStringSetpoint()).isEqualTo("setpoint1");
        assertThat(models.get(0).getNumericalSetpoint()).isNull();
        assertThat(models.get(0).getValueConditionMode()).isEqualTo(ValueConditionMode.EQUALS.getAsInt());
    }


    @Test
    void getNumberOfAlarms()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
        assertThat(alarmService.getNumberOfAlarms()).isEqualTo(2);
    }


    @Test
    void getNumberOfEnabledAlarms()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
        assertThat(alarmService.getNumberOfEnabledAlarms()).isEqualTo(1);
    }


    @Test
    void getNumberOfDisabledAlarms()
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(false);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        AlarmModel model2 = new AlarmModel();
        model2.setName("alarm2");
        model2.setDescription("desc2");
        model2.setTagID("tag-id-2");
        model2.setEnabled(true);
        model2.setNumericalSetpoint(15.56d);
        model2.setValueConditionMode(ValueConditionMode.GREATER_OR_EQUAL_TO_SETPOINT.getAsInt());
        model2 = alarmService.save(model2);
        assertThat(alarmService.getNumberOfDisabledAlarms()).isEqualTo(1);
    }
}
