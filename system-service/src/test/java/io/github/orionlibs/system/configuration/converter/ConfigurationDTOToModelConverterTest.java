package io.github.orionlibs.system.configuration.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.system.configuration.api.SaveConfigurationRequest;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ConfigurationDTOToModelConverterTest
{
    @Test
    void convert()
    {
        SaveConfigurationRequest temp = new SaveConfigurationRequest();
        temp.setKey("somekey");
        temp.setValue("somevalue");
        ConfigurationDTOToModelConverter converter = new ConfigurationDTOToModelConverter();
        ConfigurationModel result = converter.convert(temp);
        assertThat("somekey").isEqualTo(result.getKey());
        assertThat("somevalue").isEqualTo(result.getValue());
    }


    @Test
    void convert_null()
    {
        ConfigurationDTOToModelConverter converter = new ConfigurationDTOToModelConverter();
        ConfigurationModel result = converter.convert(null);
        assertThat(result).isNull();
    }
}
