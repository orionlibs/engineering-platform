package io.github.orionlibs.database.connectivity;

import io.github.orionlibs.core.json.JSONService;
import io.github.orionlibs.database.connectivity.api.DatabaseConnectivityWebsocketController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectivityRegistry
{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private JSONService jsonService;
    private final ConcurrentMap<String, DatabaseConnectivityStatus> databaseToStatusMapper;


    public DatabaseConnectivityRegistry()
    {
        databaseToStatusMapper = new ConcurrentHashMap<>();
    }


    public List<DatabaseConnectivityStatus> getDatabases()
    {
        return List.copyOf(databaseToStatusMapper.values());
    }


    public void setStatusAndNotify(String databaseName, DatabaseConnectivityStatus status)
    {
        if(databaseToStatusMapper.get(databaseName) != null)
        {
            if(!Objects.equals(databaseToStatusMapper.get(databaseName), status))
            {
                databaseToStatusMapper.put(databaseName, status);
                publishConnectivityStats();
            }
        }
        else
        {
            databaseToStatusMapper.put(databaseName, status);
            publishConnectivityStats();
        }
    }


    public void publishConnectivityStats()
    {
        int numberOfConnectedDatabases = 0;
        int numberOfNotConnectedDatabases = 0;
        List<DatabaseConnectivityStats.DatabaseStats> databasesStats = new ArrayList<>();
        for(DatabaseConnectivityStatus status : databaseToStatusMapper.values())
        {
            if(status.getIsConnected().booleanValue())
            {
                numberOfConnectedDatabases++;
            }
            else
            {
                numberOfNotConnectedDatabases++;
            }
            String datetimeConnectionEstablished = "";
            if(status.getDatetimeConnectionEstablished() != null)
            {
                datetimeConnectionEstablished = status.getDatetimeConnectionEstablished().toString();
            }
            String datetimeConnectionLost = "";
            if(status.getDatetimeConnectionLost() != null)
            {
                datetimeConnectionLost = status.getDatetimeConnectionLost().toString();
            }
            databasesStats.add(DatabaseConnectivityStats.DatabaseStats.builder()
                            .databaseName(status.getDatabaseName())
                            .isConnected(status.getIsConnected())
                            .connectionURL(status.getConnectionURL())
                            .datetimeConnectionEstablished(datetimeConnectionEstablished)
                            .datetimeConnectionLost(datetimeConnectionLost)
                            .errorMessage(status.getErrorMessage())
                            .build());
        }
        String message = jsonService.toJson(DatabaseConnectivityStats.builder()
                        .numberOfConnectedDatabases(numberOfConnectedDatabases)
                        .numberOfNotConnectedDatabases(numberOfNotConnectedDatabases)
                        .databasesStats(databasesStats)
                        .build());
        DatabaseConnectivityWebsocketController.lastMessages.put("/topic/databases/connections", message);
        messagingTemplate.convertAndSend("/topic/databases/connections", message);
    }
}
