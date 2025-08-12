package io.github.orionlibs.core.database;

import io.github.orionlibs.core.database.connectivity.DatabaseConnectivityRegistry;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseWrapper
{
    private String databaseName;
    private String connectionURL;
    //private DataSource dataSource;
    private Connection connection;
    private DatabaseConnectivityRegistry databaseConnectivityRegistry;


    /*public DatabaseWrapper(DataSource dataSource, DatabaseConnectivityRegistry databaseConnectivityRegistry) throws SQLException
    {
        this.dataSource = dataSource;
        this.connectionURL = dataSource.getConnection().getMetaData().getURL();
        this.databaseConnectivityRegistry = databaseConnectivityRegistry;
    }*/


    public DatabaseWrapper(String databaseName, Connection connection, DatabaseConnectivityRegistry databaseConnectivityRegistry) throws SQLException
    {
        this.databaseName = databaseName;
        this.connection = connection;
        this.connectionURL = connection.getMetaData().getURL();
        this.databaseConnectivityRegistry = databaseConnectivityRegistry;
    }


    /*public boolean isConnected()
    {
        if(getConnection() == null)
        {
            return false;
        }
        try(Connection conn = getConnection())
        {
            if(conn == null)
            {
                return false;
            }
            try
            {
                if(conn.isValid(2))
                {
                    return true;
                }
            }
            catch(AbstractMethodError | SQLException ignored)
            {
            }
            try(Statement st = conn.createStatement();
                            ResultSet rs = st.executeQuery("SELECT 1"))
            {
                return rs.next();
            }
            catch(SQLException e)
            {
                return false;
            }
        }
        catch(SQLException e)
        {
            return false;
        }
    }*/


    /*private void setConnectivityStatus()
    {
        databaseConnectivityRegistry.setStatusAndNotify("", DatabaseConnectivityStatus.builder()
                        .databaseName("")
                        .connectionURL(connectionURL)
                        .isConnected(Boolean.FALSE)
                        .datetimeConnectionEstablished(null)
                        .datetimeConnectionLost(LocalDateTime.now())
                        .errorMessage("Cannot connect to the database server")
                        .build());
    }*/


    public String getDatabaseName()
    {
        return databaseName;
    }


    public Connection getConnection()
    {
        return connection;
    }


    public String getConnectionURL()
    {
        return connectionURL;
    }
}
