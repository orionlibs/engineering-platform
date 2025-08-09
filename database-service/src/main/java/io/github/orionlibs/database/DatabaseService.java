package io.github.orionlibs.database;

import io.github.orionlibs.database.model.DataProviderModel;
import io.github.orionlibs.database.model.DataProvidersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService
{
    @Autowired private DataProvidersDAO dao;


    public DataProviderModel saveDataProvider(DataProviderModel model)
    {
        return dao.saveAndFlush(model);
    }


    public long getNumberOfRegisteredDataProviders()
    {
        return dao.count();
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
