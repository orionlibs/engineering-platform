package io.github.orionlibs.system.configuration.converter;

import io.github.orionlibs.core.Converter;
import io.github.orionlibs.system.api.ConfigurationDTO;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationModelToDTOConverter implements Converter<ConfigurationModel, ConfigurationDTO>
{
    @Override
    public ConfigurationDTO convert(ConfigurationModel objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new ConfigurationDTO(objectToConvert.getKey(), objectToConvert.getValue());
    }
}
