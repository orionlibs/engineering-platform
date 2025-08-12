package io.github.orionlibs.core.event;

import io.github.orionlibs.core.json.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishableContext
{
    @Autowired private JSONService jsonService;
    @Autowired private EventPublisher eventPublisher;


    public JSONService getJsonService()
    {
        return jsonService;
    }


    public EventPublisher getEventPublisher()
    {
        return eventPublisher;
    }
}
