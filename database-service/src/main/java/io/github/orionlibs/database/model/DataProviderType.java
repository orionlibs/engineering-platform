package io.github.orionlibs.database.model;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataProviderType implements Serializable
{
    public enum Type
    {
        DATABASE,
        API
    }


    @NotNull(message = "data provider type must be provided")
    private Type type;
}
