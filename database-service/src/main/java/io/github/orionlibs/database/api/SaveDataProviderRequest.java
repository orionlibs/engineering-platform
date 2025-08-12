package io.github.orionlibs.database.api;

import io.github.orionlibs.database.model.DataProviderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "database_name must not be blank")
    private String databaseName;
    @NotNull(message = "data provider type must be provided")
    private DataProviderType.Type type;
    @NotBlank(message = "connectionURL must not be blank")
    private String connectionURL;
    private String username;
    private String password;
}
