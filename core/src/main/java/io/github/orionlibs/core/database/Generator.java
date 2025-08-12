package io.github.orionlibs.core.database;

import java.util.UUID;

public interface Generator
{
    default String generateString()
    {
        return UUID.randomUUID().toString();
    }
}
