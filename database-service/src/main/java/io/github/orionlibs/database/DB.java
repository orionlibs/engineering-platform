package io.github.orionlibs.database;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DB
{
    private static final ConcurrentMap<String, Connection> databaseConnections;

    static
    {
        databaseConnections = new ConcurrentHashMap<>();
    }

    public static void registerDatabaseConnection(String databaseName, Connection databaseConnection)
    {
        databaseConnections.put(databaseName, databaseConnection);
    }


    public static Connection get(String databaseName)
    {
        return databaseConnections.get(databaseName);
    }
}
