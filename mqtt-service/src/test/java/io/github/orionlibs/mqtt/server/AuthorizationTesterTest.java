package io.github.orionlibs.mqtt.server;

import io.github.orionlibs.mqtt.AuthorizationTester;
import io.github.orionlibs.mqtt.MQTTMessageAdapter;
import io.github.orionlibs.mqtt.Utils;
import io.github.orionlibs.mqtt.client.HiveMQClientAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
//@Execution(ExecutionMode.CONCURRENT)
public class AuthorizationTesterTest
{
    private MQTTBrokerServer brokerServer;
    private String clientID = "testClientId";
    private AuthorizationTester authorizationTester;
    private HiveMQClientAdapter clientAdapter;


    @BeforeEach
    void setUp() throws Exception
    {
        brokerServer = new MQTTBrokerServer();
        brokerServer.startBroker(true, true);
        Utils.nonblockingDelay(3);
        clientAdapter = new HiveMQClientAdapter("0.0.0.0", 1883, clientID, "admin", "password");
        authorizationTester = new AuthorizationTester(clientAdapter);
    }


    @AfterEach
    void teardown()
    {
        clientAdapter.disconnect();
        brokerServer.stopBroker();
    }


    @Test
    void testClientAuthorization() throws Exception
    {
        MQTTMessageAdapter messageAdapter = new MQTTMessageAdapter();
        authorizationTester.testSubscribeAuthorizationWithDelay("admin/topic", messageAdapter, 2);
        //assertEquals(0, listLogHandler.getLogRecords().size());
        authorizationTester.testSubscribeAuthorizationWithDelay("$shared/topic", messageAdapter, 2);
        //assertEquals(1, listLogHandler.getLogRecords().size());
        /*assertTrue(listLogHandler.getLogRecords()
                        .stream()
                        .anyMatch(record -> record.getMessage().contains("$shared")));*/
        authorizationTester.testPublishAuthorizationWithDelay("forbidden/topic", "somePayload1".getBytes(), 2);
        //assertEquals(2, listLogHandler.getLogRecords().size());
        /*assertTrue(listLogHandler.getLogRecords()
                        .stream()
                        .anyMatch(record -> record.getMessage().contains("forbidden")));*/
        authorizationTester.testPublishAuthorizationWithDelay("admin/topic", "somePayload1".getBytes(), 2);
        //assertEquals(2, listLogHandler.getLogRecords().size());
        authorizationTester.testUnsubscribeAuthorizationWithDelay("admin/topic", 2);
    }
}
