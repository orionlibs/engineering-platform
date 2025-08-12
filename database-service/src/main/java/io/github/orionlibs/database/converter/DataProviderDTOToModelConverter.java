package io.github.orionlibs.database.converter;

import io.github.orionlibs.core.Converter;
import io.github.orionlibs.database.api.SaveDataProviderRequest;
import io.github.orionlibs.database.model.DataProviderModel;
import org.springframework.stereotype.Component;

@Component
public class DataProviderDTOToModelConverter implements Converter<SaveDataProviderRequest, DataProviderModel>
{
    @Override
    public DataProviderModel convert(SaveDataProviderRequest objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new DataProviderModel(objectToConvert.getDatabaseName(), objectToConvert.getType(), objectToConvert.getConnectionURL(), objectToConvert.getUsername(), objectToConvert.getPassword());
    }
}
