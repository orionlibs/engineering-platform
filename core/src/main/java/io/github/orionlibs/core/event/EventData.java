package io.github.orionlibs.core.event;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class EventData
{
    private String eventID;
    private String eventType;
    private String message;
    private EventData parentContext;
    private Map<String, Object> eventParameters;
    private LocalDateTime timestamp;


    public EventData(String eventType, String message, Map<String, Object> eventParameters)
    {
        this(eventType, message, eventParameters, null);
    }


    public EventData(String eventType, String message, Map<String, Object> eventParameters, EventData parentContext)
    {
        this.eventID = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.message = message;
        this.parentContext = parentContext;
        this.eventParameters = eventParameters;
        this.timestamp = LocalDateTime.now();
    }


    public String getEventID()
    {
        return this.eventID;
    }


    public String getMessage()
    {
        return message;
    }


    public String getEventType()
    {
        return eventType;
    }


    public EventData getParentContext()
    {
        return this.parentContext;
    }


    public Map<String, Object> getEventParameters()
    {
        return this.eventParameters;
    }


    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }
}
