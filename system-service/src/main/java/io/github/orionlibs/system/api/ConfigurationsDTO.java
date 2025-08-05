package io.github.orionlibs.system.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

@Schema(name = "ConfigurationsDTO", description = "Wrapper for a list of system configurations")
public record ConfigurationsDTO(List<ConfigurationDTO> configurations) implements Serializable
{
}
