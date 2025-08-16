package io.github.orionlibs.alarm.model;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.alarm.AlarmService;
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
public class AlarmEventsDAOTest
{
    @Autowired AlarmEventsDAO dao;
    @Autowired AlarmService alarmService;
    AlarmModel alarm;


    @BeforeEach
    void setup()
    {
        alarmService.deleteAll();
        dao.deleteAll();
        alarm = new AlarmModel();
        alarm.setName("alarm1");
        alarm.setDescription("desc1");
        alarm.setEnabled(false);
        alarm.setStringSetpoint("setpoint1");
        alarm.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        alarm = alarmService.save(alarm);
    }


    @Test
    void findById()
    {
        AlarmEventModel model1 = new AlarmEventModel();
        model1.setAlarm(alarm);
        model1.setAcknowledged(true);
        model1.setMessage("event1");
        model1 = dao.saveAndFlush(model1);
        AlarmEventModel model2 = new AlarmEventModel();
        model2.setAlarm(alarm);
        model2.setAcknowledged(false);
        model2.setMessage("event2");
        model2 = dao.saveAndFlush(model2);
        Optional<AlarmEventModel> modelWrap = dao.findById(model1.getId());
        assertThat(modelWrap.get().getMessage()).isEqualTo("event1");
        assertThat(modelWrap.get().isAcknowledged()).isTrue();
        assertThat(modelWrap.get().getAlarm().getId()).isEqualTo(alarm.getId());
        assertThat(modelWrap.get().getCreatedAt()).isNotNull();
    }


    @Test
    void findAll()
    {
        AlarmEventModel model1 = new AlarmEventModel();
        model1.setAlarm(alarm);
        model1.setAcknowledged(true);
        model1.setMessage("event1");
        model1 = dao.saveAndFlush(model1);
        AlarmEventModel model2 = new AlarmEventModel();
        model2.setAlarm(alarm);
        model2.setAcknowledged(false);
        model2.setMessage("event2");
        model2 = dao.saveAndFlush(model2);
        List<AlarmEventModel> models = dao.findAll();
        assertThat(models.get(0).getMessage()).isEqualTo("event1");
        assertThat(models.get(0).isAcknowledged()).isTrue();
        assertThat(models.get(0).getAlarm().getId()).isEqualTo(alarm.getId());
        assertThat(models.get(0).getCreatedAt()).isNotNull();
        assertThat(models.get(1).getMessage()).isEqualTo("event2");
        assertThat(models.get(1).isAcknowledged()).isFalse();
        assertThat(models.get(1).getAlarm().getId()).isEqualTo(alarm.getId());
        assertThat(models.get(1).getCreatedAt()).isNotNull();
    }
}
