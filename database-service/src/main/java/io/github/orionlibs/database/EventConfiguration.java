package io.github.orionlibs.database;

import io.github.orionlibs.core.database.connectivity.EventDatabaseConnected;
import io.github.orionlibs.core.database.connectivity.EventDatabaseDisconnected;
import io.github.orionlibs.core.event.Subscribable;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration implements Subscribable
{
    public EventConfiguration()
    {
        subscribe(EventDatabaseConnected.EVENT_NAME);
        subscribe(EventDatabaseDisconnected.EVENT_NAME);
    }
}
