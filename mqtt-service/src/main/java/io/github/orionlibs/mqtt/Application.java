package io.github.orionlibs.mqtt;

import io.github.orionlibs.mqtt.server.MQTTBrokerServer;
import java.net.URISyntaxException;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class Application
{
    public static void main(String[] args) throws URISyntaxException, ExecutionException, InterruptedException
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        MQTTBrokerServer brokerServer = new MQTTBrokerServer();
        brokerServer.startBroker(false, false);
    }
}