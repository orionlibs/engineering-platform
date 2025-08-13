package io.github.orionlibs.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishableContext
{
    @Autowired private EventPublisher eventPublisher;


    public EventPublisher getEventPublisher()
    {
        return eventPublisher;
    }
}
