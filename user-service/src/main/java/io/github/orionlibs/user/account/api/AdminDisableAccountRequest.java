package io.github.orionlibs.user.account.api;

import io.github.orionlibs.core.uuid.ValidUUID;
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
public class AdminDisableAccountRequest
{
    @NotBlank(message = "userID cannot be blank")
    @ValidUUID
    private String userID;
}
