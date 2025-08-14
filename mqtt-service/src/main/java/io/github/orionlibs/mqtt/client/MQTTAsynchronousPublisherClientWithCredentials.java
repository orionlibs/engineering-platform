package io.github.orionlibs.mqtt.client;

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import io.github.orionlibs.mqtt.MQTTClientType;
import io.github.orionlibs.mqtt.MQTTUserProperties;
import java.nio.charset.StandardCharsets;

public class MQTTAsynchronousPublisherClientWithCredentials
{
    private Mqtt5AsyncClient client;


    public MQTTAsynchronousPublisherClientWithCredentials(String brokerUrl, int port, String clientId, String username, String password)
    {
        this.client = Mqtt5Client.builder()
                        .identifier(clientId)
                        .simpleAuth(Mqtt5SimpleAuth.builder()
                                        .username(username)
                                        .password(password.getBytes(StandardCharsets.UTF_8))
                                        .build())
                        .serverHost(brokerUrl)
                        .serverPort(port)
                        .buildAsync();
        Mqtt5Connect connectMessage = Mqtt5Connect.builder()
                        .userProperties()
                        .add(MQTTUserProperties.CLIENT_TYPE, MQTTClientType.PUBLISHER.get())
                        .applyUserProperties()
                        .build();
        client.connect(connectMessage).thenRun(() -> {
            System.out.println("Successfully connected publisher!");
        });
    }


    public Mqtt5AsyncClient getClient()
    {
        return client;
    }
}
