package io.github.orionlibs.database;

import io.github.orionlibs.database.connectivity.DatabaseConnectivityRegistry;
import io.github.orionlibs.database.connectivity.DatabaseConnectivityStatus;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class DatabaseWrapper
{
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


    public DatabaseWrapper(Connection connection, DatabaseConnectivityRegistry databaseConnectivityRegistry) throws SQLException
    {
        this.connection = connection;
        this.connectionURL = connection.getMetaData().getURL();
        this.databaseConnectivityRegistry = databaseConnectivityRegistry;
    }


    public boolean isConnected()
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
    }


    private void setConnectivityStatus()
    {
        databaseConnectivityRegistry.setStatusAndNotify("", DatabaseConnectivityStatus.builder()
                        .databaseName("")
                        .connectionURL(connectionURL)
                        .isConnected(Boolean.FALSE)
                        .datetimeConnectionEstablished(null)
                        .datetimeConnectionLost(LocalDateTime.now())
                        .errorMessage("Cannot connect to the database server")
                        .build());
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
