package io.github.orionlibs.user.setting.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AdminUpdateUserSettingRequest
{
    private String settingValue;
}
