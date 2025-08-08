package io.github.orionlibs.database.api;

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
public class SaveDataProviderRequest
{
    @NotBlank(message = "connectionURL must not be blank")
    private String connectionURL;
    private String username;
    private String password;
}
