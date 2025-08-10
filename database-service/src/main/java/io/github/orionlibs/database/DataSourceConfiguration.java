package io.github.orionlibs.database;

import io.github.orionlibs.database.connectivity.DatabaseConnectivityMonitor;
import io.github.orionlibs.database.connectivity.DatabaseConnectivityRegistry;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration
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
        DatabaseWrapper wrapper = new DatabaseWrapper(realDs.getConnection(), databaseConnectivityRegistry);
        DatabaseConnectivityMonitor databaseConnectivityMonitor = new DatabaseConnectivityMonitor(databaseConnectivityRegistry, wrapper);
        context.getAutowireCapableBeanFactory().initializeBean(databaseConnectivityMonitor, "DatabaseServiceDatabaseConnectionMonitor");
        context.getAutowireCapableBeanFactory().autowireBean(databaseConnectivityMonitor);
        return ProxyDataSourceBuilder
                        .create(realDs)
                        .name("DB-PROXY")
                        .listener(new QueryExecutionListener()
                        {
                            @Override
                            public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList)
                            {
                                //Instant now = Instant.now();
                                for(QueryInfo qi : queryInfoList)
                                {
                                    String sql = qi.getQuery();
                                    //System.out.println("-------SQL running: " + sql);
                                }
                            }


                            @Override
                            public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList)
                            {
                                //long durationMs = execInfo.getElapsedTime(); // in ms
                                //Instant now = Instant.now();
                                for(QueryInfo qi : queryInfoList)
                                {
                                    String sql = qi.getQuery();
                                    // persist asynchronously to avoid blocking the thread that’s doing your business logic
                                    //System.out.println("-------SQL ran: " + sql);
                                    //repo.save(new QueryLog(null, sql, now, durationMs));
                                }
                            }
                        })
                        .build();
    }


    @Bean
    public DataSource loggingDataSource(@Qualifier("loggingDataSourceProperties") DataSourceProperties props) throws SQLException
    {
        DataSource realDs = props.initializeDataSourceBuilder().build();
        DatabaseWrapper wrapper = new DatabaseWrapper(realDs.getConnection(), databaseConnectivityRegistry);
        DatabaseConnectivityMonitor databaseConnectivityMonitor = new DatabaseConnectivityMonitor(databaseConnectivityRegistry, wrapper);
        context.getAutowireCapableBeanFactory().initializeBean(databaseConnectivityMonitor, "DatabaseServiceLggingDatabaseConnectionMonitor");
        context.getAutowireCapableBeanFactory().autowireBean(databaseConnectivityMonitor);
        return realDs;
    }
}
