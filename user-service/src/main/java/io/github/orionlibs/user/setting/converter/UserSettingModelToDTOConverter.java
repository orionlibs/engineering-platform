package io.github.orionlibs.user.setting.converter;

import io.github.orionlibs.core.converter.Converter;
import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import io.github.orionlibs.user.setting.api.UserSettingDTO;
import org.springframework.stereotype.Component;

@Component
public class UserSettingModelToDTOConverter implements Converter<UserSettingsModel, UserSettingDTO>
{
    @Override
    public UserSettingDTO convert(UserSettingsModel objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new UserSettingDTO(objectToConvert.getId().toString(), objectToConvert.getSettingName(), objectToConvert.getSettingValue());
    }
}
