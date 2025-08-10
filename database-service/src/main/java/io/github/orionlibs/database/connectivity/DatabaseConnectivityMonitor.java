package io.github.orionlibs.database.connectivity;

import io.github.orionlibs.database.DatabaseWrapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class DatabaseConnectivityMonitor
{
    private DatabaseWrapper wrapper;
    private DatabaseConnectivityRegistry databaseConnectivityRegistry;
    private final Connection connection;
    private boolean wasDown = false;


    public DatabaseConnectivityMonitor(DatabaseConnectivityRegistry databaseConnectivityRegistry, DatabaseWrapper wrapper)
    {
        this.wrapper = wrapper;
        this.databaseConnectivityRegistry = databaseConnectivityRegistry;
        this.connection = wrapper.getConnection();
    }


    public void startMonitoring()
    {
        //this is just a trigger to ensure @Scheduled starts
    }


    @Async
    @Scheduled(fixedDelay = 10000)
    public void checkConnection()
    {
        try
        {
            try(Connection connection = getConnection())
            {
                if(wasDown)
                {
                    wasDown = false;
                    notifyConnectionRestored();
                }
            }
            catch(SQLException e)
            {
                System.err.println(e.getMessage());
                e.printStackTrace();
                if(!wasDown)
                {
                    wasDown = true;
                    notifyConnectionLost();
                }
            }
        }
        catch(Exception e)
        {
            System.err.println("Error in scheduled database connectivity monitor task: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void notifyConnectionLost()
    {
        //System.out.println(wrapper.getDatabaseName() + " Database connection lost!");
        databaseConnectivityRegistry.setStatusAndNotify(wrapper.getConnectionURL(), DatabaseConnectivityStatus.builder()
                        //.databaseName(wrapper.getDatabaseName())
                        .connectionURL(wrapper.getConnectionURL())
                        .isConnected(Boolean.FALSE)
                        .datetimeConnectionEstablished(null)
                        .datetimeConnectionLost(LocalDateTime.now())
                        .errorMessage("Cannot connect to the database server")
                        .build());
    }


    private void notifyConnectionRestored()
    {
        //System.out.println(wrapper.getDatabaseName() + " Database connection restored!");
        databaseConnectivityRegistry.setStatusAndNotify(wrapper.getConnectionURL(), DatabaseConnectivityStatus.builder()
                        //.databaseName(wrapper.getDatabaseName())
                        .connectionURL(wrapper.getConnectionURL())
                        .isConnected(Boolean.TRUE)
                        .datetimeConnectionEstablished(LocalDateTime.now())
                        .datetimeConnectionLost(null)
                        .errorMessage("")
                        .build());
    }


    public Connection getConnection()
    {
        return connection;
    }
}
