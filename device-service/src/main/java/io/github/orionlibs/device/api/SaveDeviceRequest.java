package io.github.orionlibs.device.api;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object used as request bean in the API holding the data of a device to be updated
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SaveDeviceRequest implements Serializable
{
    private String deviceName;
    private String deviceDescription;
}
