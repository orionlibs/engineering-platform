package io.github.orionlibs.core.database.store_and_forward;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StoreAndForwardEngineRegistry
{
    private static final ConcurrentMap<String, StoreAndForwardEngine> engines;

    static
    {
        engines = new ConcurrentHashMap<>();
    }

    public static void registerStoreAndForwardEngine(String databaseName, StoreAndForwardEngine engine)
    {
        engines.put(databaseName, engine);
    }


    public static StoreAndForwardEngine get(String databaseName)
    {
        return engines.get(databaseName);
    }
}
