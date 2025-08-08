package io.github.orionlibs.user.setting.api;

import java.io.Serializable;
import java.util.List;

public record UserSettingsDTO(List<UserSettingDTO> settings) implements Serializable
{
}
