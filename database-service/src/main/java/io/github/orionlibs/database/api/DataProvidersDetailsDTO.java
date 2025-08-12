package io.github.orionlibs.database.api;

import java.io.Serializable;
import java.util.List;

public record DataProvidersDetailsDTO(List<DataProviderDetailsDTO> databases) implements Serializable
{
}
