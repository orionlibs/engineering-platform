package io.github.orionlibs.user.password.api;

import io.github.orionlibs.core.user.Password;
import io.github.orionlibs.core.user.ValidUUID;
import jakarta.validation.constraints.NotBlank;
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
public class AdminUpdatePasswordRequest
{
    @Password
    private String password;
    @NotBlank(message = "userID cannot be blank")
    @ValidUUID
    private String userID;
}
