package io.github.orionlibs.device.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record DeviceDTO(@JsonProperty("device_name") String deviceName, @JsonProperty("device_description") String deviceDescription) implements Serializable
{
}
