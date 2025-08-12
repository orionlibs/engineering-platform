package io.github.orionlibs.core.database.connectivity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DatabaseConnectivityStats
{
    private Integer numberOfConnectedDatabases;
    private Integer numberOfNotConnectedDatabases;
    private List<DatabaseStats> databasesStats;


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class DatabaseStats
    {
        private String databaseName;
        private String connectionURL;
        private Boolean isConnected;
        private String datetimeConnectionEstablished;
        private String datetimeConnectionLost;
        private String errorMessage;
    }
}
