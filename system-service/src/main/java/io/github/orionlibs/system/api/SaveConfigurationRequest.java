package io.github.orionlibs.system.api;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object used as request bean in the API holding the data of a configuration to be updated
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SaveConfigurationRequest implements Serializable
{
    @NotBlank(message = "configuration key must not be blank")
    private String key;
    private String value;
}
