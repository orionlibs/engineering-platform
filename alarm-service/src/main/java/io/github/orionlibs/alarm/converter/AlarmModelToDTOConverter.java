package io.github.orionlibs.alarm.converter;

import io.github.orionlibs.alarm.ValueConditionMode;
import io.github.orionlibs.alarm.api.AlarmDTO;
import io.github.orionlibs.alarm.model.AlarmModel;
import io.github.orionlibs.core.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlarmModelToDTOConverter implements Converter<AlarmModel, AlarmDTO>
{
    @Override
    public AlarmDTO convert(AlarmModel objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        String setpoint = objectToConvert.getStringSetpoint();
        if(objectToConvert.getNumericalSetpoint() != null)
        {
            setpoint = objectToConvert.getNumericalSetpoint().toString();
        }
        return new AlarmDTO(objectToConvert.getId().toString(),
                        objectToConvert.getName(),
                        objectToConvert.getDescription(),
                        objectToConvert.getTagID(),
                        objectToConvert.isEnabled(),
                        setpoint,
                        ValueConditionMode.getEnumNameForIntegerValue(objectToConvert.getValueConditionMode()));
    }
}
