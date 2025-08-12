package io.github.orionlibs.database.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record DataProviderDetailsDTO(String id,
                                     @JsonProperty("database_name") String databaseName,
                                     @JsonProperty("connection_url") String connectionURL) implements Serializable
{
}
