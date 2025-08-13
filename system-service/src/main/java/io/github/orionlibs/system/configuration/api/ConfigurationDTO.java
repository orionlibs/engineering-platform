package io.github.orionlibs.system.configuration.api;

import java.io.Serializable;

public record ConfigurationDTO(String key, String value) implements Serializable
{
}
