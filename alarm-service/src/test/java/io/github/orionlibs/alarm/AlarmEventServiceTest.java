package io.github.orionlibs.alarm;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.alarm.model.AlarmEventModel;
import io.github.orionlibs.alarm.model.AlarmModel;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AlarmEventServiceTest
{
    @Autowired AlarmService alarmService;
    @Autowired AlarmEventService alarmEventService;
    AlarmModel alarm;


    @BeforeEach
    void setup()
    {
        alarmService.deleteAll();
        alarmEventService.deleteAll();
        alarm = new AlarmModel();
        alarm.setName("alarm1");
        alarm.setDescription("desc1");
        alarm.setEnabled(false);
        alarm.setStringSetpoint("setpoint1");
        alarm.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        alarm = alarmService.save(alarm);
    }


    @Test
    void getByID()
    {
        AlarmEventModel model1 = new AlarmEventModel();
        model1.setAlarm(alarm);
        model1.setAcknowledged(true);
        model1.setMessage("event1");
        model1 = alarmEventService.save(model1);
        AlarmEventModel model2 = new AlarmEventModel();
        model2.setAlarm(alarm);
        model2.setAcknowledged(false);
        model2.setMessage("event2");
        model2 = alarmEventService.save(model2);
        Optional<AlarmEventModel> modelWrap = alarmEventService.getByID(model1.getId().toString());
        assertThat(modelWrap.get().getMessage()).isEqualTo("event1");
    }
}
