package io.github.orionlibs.core.event;

import io.github.orionlibs.core.json.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishableContext
{
    private JSONService jsonService;
    private EventPublisher eventPublisher;


    @Autowired
    public void setJsonService(JSONService jsonService)
    {
        this.jsonService = jsonService;
    }


    @Autowired
    public void setEventPublisher(EventPublisher publisher)
    {
        this.eventPublisher = publisher;
    }


    public JSONService json()
    {
        return jsonService;
    }


    public EventPublisher publisher()
    {
        return eventPublisher;
    }
}
