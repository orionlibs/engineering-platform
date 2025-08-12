package io.github.orionlibs.database;

import io.github.orionlibs.core.database.connectivity.EventDatabaseConnected;
import io.github.orionlibs.core.database.connectivity.EventDatabaseDisconnected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectivityEventMonitor
{
    @Autowired private DatabaseConnectivityService databaseConnectivityService;


    public void receiveEvent(String eventName, String eventContextAsJSON)
    {
        if(EventDatabaseConnected.EVENT_NAME.equals(eventName))
        {
            databaseConnectivityService.markDatabaseAsConnected(eventContextAsJSON);
        }
        else if(EventDatabaseDisconnected.EVENT_NAME.equals(eventName))
        {
            databaseConnectivityService.markDatabaseAsDisconnected(eventContextAsJSON);
        }
    }
}
