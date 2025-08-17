package io.github.orionlibs.alarm.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.alarm.ValueConditionMode;
import io.github.orionlibs.alarm.api.AlarmDTO;
import io.github.orionlibs.alarm.model.AlarmModel;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class AlarmModelToDTOConverterTest
{
    @Test
    void convert_stringSetpoint()
    {
        AlarmModel temp = new AlarmModel();
        temp.setId(UUID.randomUUID());
        temp.setName("alarm1");
        temp.setDescription("desc1");
        temp.setTagID("tag-id-1");
        temp.setEnabled(true);
        temp.setStringSetpoint("setpoint1");
        temp.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        AlarmModelToDTOConverter converter = new AlarmModelToDTOConverter();
        AlarmDTO result = converter.convert(temp);
        assertThat(result.id().length()).isGreaterThan(20);
        assertThat(result.name()).isEqualTo("alarm1");
        assertThat(result.description()).isEqualTo("desc1");
        assertThat(result.tagID()).isEqualTo("tag-id-1");
        assertThat(result.isEnabled()).isTrue();
        assertThat(result.setpoint()).isEqualTo("setpoint1");
        assertThat(result.valueConditionMode()).isEqualTo("EQUALS");
    }


    @Test
    void convert_numericalSetpoint()
    {
        AlarmModel temp = new AlarmModel();
        temp.setId(UUID.randomUUID());
        temp.setName("alarm1");
        temp.setDescription("desc1");
        temp.setTagID("tag-id-1");
        temp.setEnabled(true);
        temp.setNumericalSetpoint(12.34d);
        temp.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        AlarmModelToDTOConverter converter = new AlarmModelToDTOConverter();
        AlarmDTO result = converter.convert(temp);
        assertThat(result.id().length()).isGreaterThan(20);
        assertThat(result.name()).isEqualTo("alarm1");
        assertThat(result.description()).isEqualTo("desc1");
        assertThat(result.tagID()).isEqualTo("tag-id-1");
        assertThat(result.isEnabled()).isTrue();
        assertThat(result.setpoint()).isEqualTo("12.34");
        assertThat(result.valueConditionMode()).isEqualTo("EQUALS");
    }


    @Test
    void convert_null()
    {
        AlarmModelToDTOConverter converter = new AlarmModelToDTOConverter();
        AlarmDTO result = converter.convert(null);
        assertThat(result).isNull();
    }
}
