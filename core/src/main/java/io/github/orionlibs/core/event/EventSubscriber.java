package io.github.orionlibs.core.event;

import io.github.orionlibs.core.Logger;

public interface EventSubscriber
{
    void subscribe(String topic);


    class EventSubscriberFake implements EventSubscriber
    {
        @Override
        public void subscribe(String topic)
        {
            //fake implementation
            Logger.info("subscribing to event topic {}", topic);
        }
    }
}
