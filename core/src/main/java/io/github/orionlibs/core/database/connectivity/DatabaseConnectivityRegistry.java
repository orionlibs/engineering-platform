package io.github.orionlibs.core.database.connectivity;

import io.github.orionlibs.core.database.connectivity.api.DatabaseConnectivityWebsocketController;
import io.github.orionlibs.core.json.JSONService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectivityRegistry
{
    private static final String TOPIC = "/topic/databases/connections";
    @Autowired private SimpMessagingTemplate messagingTemplate;
    //private final ConcurrentMap<String, DatabaseConnectivityStatus> databaseToStatusMapper;
    //mapper of database name to true (connected) or false (disconnected)
    private final ConcurrentMap<String, Boolean> databaseToStatusMapper;


    public DatabaseConnectivityRegistry()
    {
        databaseToStatusMapper = new ConcurrentHashMap<>();
    }


    /*public List<DatabaseConnectivityStatus> getDatabases()
    {
        return List.copyOf(databaseToStatusMapper.values());
    }*/
    public List<Boolean> getDatabases()
    {
        return List.copyOf(databaseToStatusMapper.values());
    }


    public long getNumberOfConnectedDatabases()
    {
        return databaseToStatusMapper.values().stream().filter(v -> v.booleanValue() == true).count();
    }


    public long getNumberOfDisconnectedDatabases()
    {
        return databaseToStatusMapper.values().stream().filter(v -> v.booleanValue() == false).count();
    }


    public void setStatusAndNotify(String databaseName, boolean isConnected)
    {
        databaseToStatusMapper.put(databaseName, isConnected);
        publishConnectivityStats();
    }


    /*public void setStatusAndNotify(String databaseName, DatabaseConnectivityStatus status)
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
    }*/
    public void publishConnectivityStats()
    {
        int numberOfConnectedDatabases = 0;
        int numberOfNotConnectedDatabases = 0;
        List<DatabaseConnectivityStats.DatabaseStats> databasesStats = new ArrayList<>();
        for(Map.Entry<String, Boolean> status : databaseToStatusMapper.entrySet())
        {
            if(status.getValue().booleanValue())
            {
                numberOfConnectedDatabases++;
            }
            else
            {
                numberOfNotConnectedDatabases++;
            }
            databasesStats.add(DatabaseConnectivityStats.DatabaseStats.builder()
                            .databaseName(status.getKey())
                            .isConnected(status.getValue())
                            //.connectionURL(status.getConnectionURL())
                            //.datetimeConnectionEstablished(datetimeConnectionEstablished)
                            //.datetimeConnectionLost(datetimeConnectionLost)
                            //.errorMessage(status.getErrorMessage())
                            .build());
        }
        String message = JSONService.convertObjectToJSON(DatabaseConnectivityStats.builder()
                        .numberOfConnectedDatabases(numberOfConnectedDatabases)
                        .numberOfNotConnectedDatabases(numberOfNotConnectedDatabases)
                        .databasesStats(databasesStats)
                        .build());
        DatabaseConnectivityWebsocketController.lastMessages.put(TOPIC, message);
        messagingTemplate.convertAndSend(TOPIC, message);
    }

    /*public void publishConnectivityStats()
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
        DatabaseConnectivityWebsocketController.lastMessages.put(TOPIC, message);
        messagingTemplate.convertAndSend(TOPIC, message);
    }*/
}
