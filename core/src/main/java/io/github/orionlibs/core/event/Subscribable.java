package io.github.orionlibs.core.event;

import java.util.Objects;

public interface Subscribable
{
    default void subscribe(String topic)
    {
        Objects.requireNonNull(topic, "topic must not be null");
        //SubscribableContext.subscriber().subscribe(topic);
    }
}
