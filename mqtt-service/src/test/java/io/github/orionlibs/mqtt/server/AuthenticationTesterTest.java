package io.github.orionlibs.mqtt.server;

import io.github.orionlibs.mqtt.AuthenticationTester;
import io.github.orionlibs.mqtt.Utils;
import io.github.orionlibs.mqtt.client.HiveMQClientAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
//@Execution(ExecutionMode.CONCURRENT)
public class AuthenticationTesterTest
{
    private MQTTBrokerServer brokerServer;
    private String clientID = "testClientId";
    private AuthenticationTester authenticationTester;
    private HiveMQClientAdapter clientAdapter;


    @BeforeEach
    void setUp() throws Exception
    {
        brokerServer = new MQTTBrokerServer();
        brokerServer.startBroker(true, false);
        Utils.nonblockingDelay(3);
        clientAdapter = new HiveMQClientAdapter();
        authenticationTester = new AuthenticationTester(clientAdapter);
    }


    @AfterEach
    void teardown()
    {
        clientAdapter.disconnect();
        brokerServer.stopBroker();
    }


    @Test
    void testClientAuthentication() throws Exception
    {
        authenticationTester.testCredentialsWithDelay("0.0.0.0", 1883, clientID, "admin", "password", 2);
        //assertEquals(0, listLogHandler.getLogRecords().size());
        authenticationTester.testCredentialsWithDelay("0.0.0.0", 1883, clientID, "admin", "wrongpassword", 2);
        //assertEquals(1, listLogHandler.getLogRecords().size());
        /*assertTrue(listLogHandler.getLogRecords()
                        .stream()
                        .anyMatch(record -> record.getMessage().contains("NOT_AUTHORIZED_0")));*/
        authenticationTester.testCredentialsWithDelay("0.0.0.0", 1883, clientID, "wronguser", "password", 2);
        //assertEquals(2, listLogHandler.getLogRecords().size());
        /*assertTrue(listLogHandler.getLogRecords()
                        .stream()
                        .anyMatch(record -> record.getMessage().contains("NOT_AUTHORIZED_1")));*/
        authenticationTester.testCredentialsWithDelay("0.0.0.0", 1883, clientID, "wronguser", "wrongpassword", 2);
        //assertEquals(3, listLogHandler.getLogRecords().size());
        /*assertTrue(listLogHandler.getLogRecords()
                        .stream()
                        .anyMatch(record -> record.getMessage().contains("NOT_AUTHORIZED_2")));*/
    }
}
