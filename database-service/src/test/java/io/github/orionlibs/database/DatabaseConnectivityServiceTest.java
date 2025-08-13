package io.github.orionlibs.database;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.database.connectivity.EventDatabaseConnected;
import io.github.orionlibs.core.database.connectivity.EventDatabaseDisconnected;
import io.github.orionlibs.core.json.JSONService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseConnectivityServiceTest
{
    @Autowired DatabaseConnectivityService databaseConnectivityService;


    @BeforeEach
    void setup()
    {
        databaseConnectivityService.resetConnectivityNumbers();
    }


    @Test
    void markDatabaseAsConnected()
    {
        assertThat(databaseConnectivityService.getNumberOfConnectedDataProviders()).isEqualTo(0L);
        databaseConnectivityService.markDatabaseAsConnected(JSONService.convertObjectToJSON(EventDatabaseConnected.builder()
                        .databaseName("uns")
                        .build()));
        assertThat(databaseConnectivityService.getNumberOfConnectedDataProviders()).isEqualTo(1L);
        assertThat(databaseConnectivityService.getNumberOfDisconnectedDataProviders()).isEqualTo(0L);
    }


    @Test
    void markDatabaseAsDisconnected()
    {
        assertThat(databaseConnectivityService.getNumberOfConnectedDataProviders()).isEqualTo(0L);
        databaseConnectivityService.markDatabaseAsDisconnected(JSONService.convertObjectToJSON(EventDatabaseDisconnected.builder()
                        .databaseName("uns")
                        .build()));
        assertThat(databaseConnectivityService.getNumberOfConnectedDataProviders()).isEqualTo(0L);
        assertThat(databaseConnectivityService.getNumberOfDisconnectedDataProviders()).isEqualTo(1L);
    }
}
