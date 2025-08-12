package io.github.orionlibs.core.database.connectivity;

import io.github.orionlibs.core.event.Event;
import java.io.Serializable;
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
@Event
public class EventDatabaseConnected implements Serializable
{
    public static final String EVENT_NAME = "database server connected";
    private String databaseName;
}
