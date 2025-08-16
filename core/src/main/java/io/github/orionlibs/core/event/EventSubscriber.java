package io.github.orionlibs.core.event;

import io.github.orionlibs.core.Logger;

public interface EventSubscriber
{
    void subscribe(String topic);


    void process(EventData event);


    class EventSubscriberFake implements EventSubscriber
    {
        @Override
        public void subscribe(String topic)
        {
            //fake implementation
            Logger.info("subscribing to event topic {}", topic);
        }


        @Override
        public void process(EventData event)
        {
            Logger.info("processing event {}", event.getEventID());
        }
    }
}
