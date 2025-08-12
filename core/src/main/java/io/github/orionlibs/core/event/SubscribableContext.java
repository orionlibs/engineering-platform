package io.github.orionlibs.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscribableContext
{
    @Autowired private EventSubscriber eventSubscriber;


    public EventSubscriber subscriber()
    {
        return eventSubscriber;
    }
}
