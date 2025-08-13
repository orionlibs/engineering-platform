package io.github.orionlibs.system.configuration.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.system.api.ConfigurationDTO;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ConfigurationModelToDTOConverterTest
{
    @Test
    void convert()
    {
        ConfigurationModel temp = new ConfigurationModel();
        temp.setKey("somekey");
        temp.setValue("somevalue");
        ConfigurationModelToDTOConverter converter = new ConfigurationModelToDTOConverter();
        ConfigurationDTO result = converter.convert(temp);
        assertThat("somekey").isEqualTo(result.key());
        assertThat("somevalue").isEqualTo(result.value());
    }


    @Test
    void convert_null()
    {
        ConfigurationModelToDTOConverter converter = new ConfigurationModelToDTOConverter();
        ConfigurationDTO result = converter.convert(null);
        assertThat(result).isNull();
    }
}
