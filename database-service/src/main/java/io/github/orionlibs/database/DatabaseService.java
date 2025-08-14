package io.github.orionlibs.database;

import io.github.orionlibs.core.database.DatabaseWrapper;
import io.github.orionlibs.core.database.connectivity.DatabaseConnectivityMonitor;
import io.github.orionlibs.core.database.connectivity.DatabaseConnectivityRegistry;
import io.github.orionlibs.database.api.SaveDataProviderRequest;
import io.github.orionlibs.database.converter.DataProviderDTOToModelConverter;
import io.github.orionlibs.database.model.DataProviderModel;
import io.github.orionlibs.database.model.DataProvidersDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService
{
    @Autowired private DataProvidersDAO dao;
    @Autowired private ApplicationContext context;
    @Autowired private DatabaseConnectivityRegistry databaseConnectivityRegistry;
    @Autowired private DataProviderDTOToModelConverter dataProviderDTOToModelConverter;


    public Optional<DataProviderModel> getDataProvider(String dataProviderID)
    {
        return dao.findById(UUID.fromString(dataProviderID));
    }


    public List<DataProviderModel> getDataProviders()
    {
        return dao.findAll();
    }


    public DataProviderModel saveDataProvider(SaveDataProviderRequest requestBean)
    {
        DataProviderModel model = dataProviderDTOToModelConverter.convert(requestBean);
        return dao.saveAndFlush(model);
    }


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


    public Connection getDatabaseConnection(String databaseName, String databaseURL, String databaseUsername, String databasePassword) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        DatabaseWrapper wrapper = new DatabaseWrapper(databaseName, connection, databaseConnectivityRegistry);
        //JDBCTemplateWrapper wrapper = new JDBCTemplateWrapper("core", coreDataSource, new JdbcTemplate(coreDataSource), databaseConnectivityRegistry);
        //DB.registerDatabaseConnection("core", bean);
        //Runtime.getRuntime().addShutdownHook(new Thread(SpringDataConfiguration::shutdownCoreDatabase));
        //StoreAndForwardEngine storeAndForwardEngine = new StoreAndForwardEngine(bean, 1000);
        //StoreAndForwardEngineRegistry.registerStoreAndForwardEngine("core", storeAndForwardEngine);
        /*if(wrapper.isConnected())
        {
            //log + alert + show error in DB status page
            databaseConnectivityRegistry.setStatusAndNotify("core", DatabaseConnectivityStatus.builder()
                            .databaseName("core")
                            .connectionURL(wrapper.getConnectionURL())
                            .isConnected(Boolean.TRUE)
                            .datetimeConnectionEstablished(Instant.now())
                            .datetimeConnectionLost(null)
                            .errorMessage("")
                            .build());
        }
        else
        {
            databaseConnectivityRegistry.setStatusAndNotify("core", DatabaseConnectivityStatus.builder()
                            .databaseName("core")
                            .connectionURL(wrapper.getConnectionURL())
                            .isConnected(Boolean.FALSE)
                            .datetimeConnectionEstablished(null)
                            .datetimeConnectionLost(Instant.now())
                            .errorMessage("Cannot connect to the database server")
                            .build());
        }*/
        DatabaseConnectivityMonitor databaseConnectivityMonitor = new DatabaseConnectivityMonitor(databaseConnectivityRegistry, wrapper);
        context.getAutowireCapableBeanFactory().initializeBean(databaseConnectivityMonitor, databaseName + "DatabaseConnectionMonitor");
        context.getAutowireCapableBeanFactory().autowireBean(databaseConnectivityMonitor);
        return connection;
    }
}
