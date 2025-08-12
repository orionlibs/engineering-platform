package io.github.orionlibs.database.converter;

import io.github.orionlibs.core.Converter;
import io.github.orionlibs.database.api.DataProviderDetailsDTO;
import io.github.orionlibs.database.model.DataProviderModel;
import org.springframework.stereotype.Component;

@Component
public class DataProviderModelToDTOConverter implements Converter<DataProviderModel, DataProviderDetailsDTO>
{
    @Override
    public DataProviderDetailsDTO convert(DataProviderModel objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new DataProviderDetailsDTO(objectToConvert.getId().toString(), objectToConvert.getName(), objectToConvert.getConnectionURL());
    }
}
