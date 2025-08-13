package io.github.orionlibs.core.database;

import java.util.UUID;

public interface Generator
{
    default String generateUUID()
    {
        return UUID.randomUUID().toString();
    }
}
