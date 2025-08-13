package io.github.orionlibs.database.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.database.api.SaveDataProviderRequest;
import io.github.orionlibs.database.model.DataProviderModel;
import io.github.orionlibs.database.model.DataProviderType.Type;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DataProviderDTOToModelConverterTest
{
    @Test
    void convert()
    {
        SaveDataProviderRequest temp = new SaveDataProviderRequest();
        temp.setDatabaseName("uns");
        temp.setType(Type.DATABASE);
        DataProviderDTOToModelConverter converter = new DataProviderDTOToModelConverter();
        DataProviderModel result = converter.convert(temp);
        assertThat("uns").isEqualTo(result.getName());
    }


    @Test
    void convert_null()
    {
        DataProviderDTOToModelConverter converter = new DataProviderDTOToModelConverter();
        DataProviderModel result = converter.convert(null);
        assertThat(result).isNull();
    }
}
