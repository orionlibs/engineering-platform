package io.github.orionlibs.user.password.api;

import io.github.orionlibs.user.password.Password;
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
public class UpdatePasswordRequest
{
    @Password
    private String password;
}
