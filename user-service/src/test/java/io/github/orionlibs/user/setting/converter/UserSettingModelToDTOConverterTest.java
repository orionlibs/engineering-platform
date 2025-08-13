package io.github.orionlibs.user.setting.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import io.github.orionlibs.user.setting.api.UserSettingDTO;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UserSettingModelToDTOConverterTest
{
    @Test
    void convert()
    {
        UserSettingsModel temp = new UserSettingsModel();
        temp.setId(UUID.randomUUID());
        temp.setSettingName("setting1");
        temp.setSettingValue("value1");
        UserSettingModelToDTOConverter converter = new UserSettingModelToDTOConverter();
        UserSettingDTO result = converter.convert(temp);
        assertThat("setting1").isEqualTo(result.settingName());
        assertThat("value1").isEqualTo(result.settingValue());
    }


    @Test
    void convert_null()
    {
        UserSettingModelToDTOConverter converter = new UserSettingModelToDTOConverter();
        UserSettingDTO result = converter.convert((UserSettingsModel)null);
        assertThat(result).isNull();
        result = converter.convert((Optional<UserSettingsModel>)null);
        assertThat(result).isNull();
    }
}
