package io.github.orionlibs.database;

import static org.assertj.core.api.Assertions.assertThat;

import com.mysql.cj.jdbc.ConnectionImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DatabaseConnectionsTest
{
    @Test
    void registerDatabaseConnection()
    {
        DatabaseConnections.registerDatabaseConnection("uns", new Class1());
        assertThat(DatabaseConnections.get("uns")).isNotNull();
    }


    public static class Class1 extends ConnectionImpl
    {
    }
}
