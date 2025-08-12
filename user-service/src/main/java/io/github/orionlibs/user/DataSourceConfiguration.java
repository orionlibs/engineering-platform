package io.github.orionlibs.user;

import io.github.orionlibs.core.database.connectivity.EventDatabaseConnected;
import io.github.orionlibs.core.event.Publishable;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration implements Publishable
{
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
    public DataSource primaryDataSource(@Qualifier("primaryDataSourceProperties") DataSourceProperties props)
    {
        DataSource realDs = props.initializeDataSourceBuilder().build();
        publish(EventDatabaseConnected.EVENT_NAME, EventDatabaseConnected.builder()
                        .databaseName(props.determineDatabaseName())
                        .build());
        return realDs;
    }


    @Bean
    public DataSource loggingDataSource(@Qualifier("loggingDataSourceProperties") DataSourceProperties props)
    {
        return props.initializeDataSourceBuilder().build();
    }
}
