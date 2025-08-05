package io.github.orionlibs.system.api;

import java.io.Serializable;

public record ConfigurationDTO(String key, String value) implements Serializable
{
}
