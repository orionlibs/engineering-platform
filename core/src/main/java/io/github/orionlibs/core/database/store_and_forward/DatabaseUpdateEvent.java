package io.github.orionlibs.core.database.store_and_forward;

import io.github.orionlibs.core.database.OrionModel;
import java.time.LocalDateTime;
import java.util.List;

public class DatabaseUpdateEvent
{
    private final DatabaseUpdateEventType operationType;
    private final OrionModel model;
    private final List<String> columnsToUpdate;
    private final List<String> columnsForCondition;
    private final LocalDateTime timestamp;


    public DatabaseUpdateEvent(DatabaseUpdateEventType operationType, OrionModel model, List<String> columnsToUpdate, List<String> columnsForCondition)
    {
        this.operationType = operationType;
        this.model = model;
        this.columnsToUpdate = columnsToUpdate;
        this.columnsForCondition = columnsForCondition;
        this.timestamp = LocalDateTime.now();
    }


    public DatabaseUpdateEventType getOperationType()
    {
        return operationType;
    }


    public OrionModel getModel()
    {
        return model;
    }


    public List<String> getColumnsToUpdate()
    {
        return columnsToUpdate;
    }


    public List<String> getColumnsForCondition()
    {
        return columnsForCondition;
    }


    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }
}
