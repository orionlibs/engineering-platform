package io.github.orionlibs.database;

import io.github.orionlibs.core.database.DatabaseWrapper;
import io.github.orionlibs.core.database.connectivity.DatabaseConnectivityMonitor;
import io.github.orionlibs.core.database.connectivity.DatabaseConnectivityRegistry;
import io.github.orionlibs.core.database.connectivity.EventDatabaseConnected;
import io.github.orionlibs.core.event.Publishable;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration implements Publishable
{
    @Autowired private ApplicationContext context;
    @Autowired private DatabaseConnectivityRegistry databaseConnectivityRegistry;


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties primaryDataSourceProperties()
    {
        return new DataSourceProperties();
    }


    @Bean
    @ConfigurationProperties("spring.logging-datasource")
    public DataSourceProperties loggingDataSourceProperties()
    {
        return new DataSourceProperties();
    }


    @Bean
    @Primary
    public DataSource primaryDataSource(@Qualifier("primaryDataSourceProperties") DataSourceProperties props) throws SQLException
    {
        DataSource realDs = props.initializeDataSourceBuilder().build();
        publish(EventDatabaseConnected.EVENT_NAME, EventDatabaseConnected.builder()
                        .databaseName(props.determineDatabaseName())
                        .build());
        DatabaseWrapper wrapper = new DatabaseWrapper("uns", realDs.getConnection(), databaseConnectivityRegistry);
        DatabaseConnectivityMonitor databaseConnectivityMonitor = new DatabaseConnectivityMonitor(databaseConnectivityRegistry, wrapper);
        context.getAutowireCapableBeanFactory().initializeBean(databaseConnectivityMonitor, "DatabaseServiceDatabaseConnectionMonitor");
        context.getAutowireCapableBeanFactory().autowireBean(databaseConnectivityMonitor);
        return realDs;
    }


    @Bean
    public DataSource loggingDataSource(@Qualifier("loggingDataSourceProperties") DataSourceProperties props) throws SQLException
    {
        DataSource realDs = props.initializeDataSourceBuilder().build();
        DatabaseWrapper wrapper = new DatabaseWrapper("uns_logging", realDs.getConnection(), databaseConnectivityRegistry);
        DatabaseConnectivityMonitor databaseConnectivityMonitor = new DatabaseConnectivityMonitor(databaseConnectivityRegistry, wrapper);
        context.getAutowireCapableBeanFactory().initializeBean(databaseConnectivityMonitor, "DatabaseServiceLoggingDatabaseConnectionMonitor");
        context.getAutowireCapableBeanFactory().autowireBean(databaseConnectivityMonitor);
        return realDs;
    }
}
