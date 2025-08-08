package io.github.orionlibs.user.setting.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record UserSettingDTO(String id,
                             @JsonProperty("setting_name") String settingName,
                             @JsonProperty("setting_value") String settingValue) implements Serializable
{
}
