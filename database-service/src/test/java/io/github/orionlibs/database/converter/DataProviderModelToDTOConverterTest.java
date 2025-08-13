package io.github.orionlibs.database.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.database.api.DataProviderDetailsDTO;
import io.github.orionlibs.database.model.DataProviderModel;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DataProviderModelToDTOConverterTest
{
    @Test
    void convert()
    {
        DataProviderModel temp = new DataProviderModel();
        temp.setId(UUID.randomUUID());
        temp.setName("uns");
        temp.setType("DATABASE");
        DataProviderModelToDTOConverter converter = new DataProviderModelToDTOConverter();
        DataProviderDetailsDTO result = converter.convert(temp);
        assertThat("uns").isEqualTo(result.databaseName());
    }


    @Test
    void convert_null()
    {
        DataProviderModelToDTOConverter converter = new DataProviderModelToDTOConverter();
        DataProviderDetailsDTO result = converter.convert(null);
        assertThat(result).isNull();
    }
}
