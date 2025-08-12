package io.github.orionlibs.core.event;

import io.github.orionlibs.core.json.JSONService;
import java.util.Objects;

public interface Publishable
{
    default void publish(String eventName, Object payload)
    {
        Objects.requireNonNull(eventName, "eventName must not be null");
        String json = JSONService.convertObjectToJSON(payload);
        //publisher().publish(eventName, json);
    }
}
