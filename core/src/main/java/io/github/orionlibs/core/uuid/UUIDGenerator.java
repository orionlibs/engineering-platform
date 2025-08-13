package io.github.orionlibs.core.uuid;

import java.util.UUID;

public interface UUIDGenerator
{
    default String generateUUID()
    {
        return UUID.randomUUID().toString();
    }
}
