package io.github.orionlibs.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscribableContext
{
    private EventSubscriber eventSubscriber;


    @Autowired
    public void setEventSubscriber(EventSubscriber subscriber)
    {
        this.eventSubscriber = subscriber;
    }


    public EventSubscriber subscriber()
    {
        return eventSubscriber;
    }
}
