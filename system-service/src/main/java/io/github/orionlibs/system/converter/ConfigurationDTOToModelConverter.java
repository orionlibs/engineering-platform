package io.github.orionlibs.system.converter;

import io.github.orionlibs.core.converter.Converter;
import io.github.orionlibs.system.api.SaveConfigurationRequest;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationDTOToModelConverter implements Converter<SaveConfigurationRequest, ConfigurationModel>
{
    @Override
    public ConfigurationModel convert(SaveConfigurationRequest objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        ConfigurationModel model = new ConfigurationModel(objectToConvert.getKey(), objectToConvert.getValue());
        return model;
    }
}
