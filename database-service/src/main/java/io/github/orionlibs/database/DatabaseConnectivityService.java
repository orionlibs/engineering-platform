package io.github.orionlibs.database;

import io.github.orionlibs.core.database.connectivity.DatabaseConnectivityRegistry;
import io.github.orionlibs.core.database.connectivity.EventDatabaseConnected;
import io.github.orionlibs.core.database.connectivity.EventDatabaseDisconnected;
import io.github.orionlibs.core.json.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectivityService
{
    @Autowired private DatabaseConnectivityRegistry databaseConnectivityRegistry;


    public void markDatabaseAsConnected(String eventContextAsJSON)
    {
        EventDatabaseConnected event = (EventDatabaseConnected)JSONService.convertJSONToObject(eventContextAsJSON, EventDatabaseConnected.class);
        databaseConnectivityRegistry.setStatusAndNotify(event.getDatabaseName(), true);
    }


    public void markDatabaseAsDisconnected(String eventContextAsJSON)
    {
        EventDatabaseDisconnected event = (EventDatabaseDisconnected)JSONService.convertJSONToObject(eventContextAsJSON, EventDatabaseDisconnected.class);
        databaseConnectivityRegistry.setStatusAndNotify(event.getDatabaseName(), false);
    }


    public long getNumberOfConnectedDataProviders()
    {
        return databaseConnectivityRegistry.getNumberOfConnectedDatabases();
    }


    public long getNumberOfDisconnectedDataProviders()
    {
        return databaseConnectivityRegistry.getNumberOfDisconnectedDatabases();
    }
}
