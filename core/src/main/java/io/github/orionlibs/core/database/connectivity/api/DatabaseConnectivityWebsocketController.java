package io.github.orionlibs.core.database.connectivity.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class DatabaseConnectivityWebsocketController
{
    @Autowired private SimpMessagingTemplate messagingTemplate;
    public static final ConcurrentMap<String, String> lastMessages;

    static
    {
        lastMessages = new ConcurrentHashMap<>();
    }

    // Store the last message for each topic when sending messages
    public void sendMessage(String topic, String message)
    {
        lastMessages.put(topic, message);
        messagingTemplate.convertAndSend(topic, message);
    }


    @MessageMapping("/lastMessage") //client calls /app/lastMessage
    public void getLastMessage(Map<String, String> payload)
    {
        String topic = payload.get("topic");
        String lastMessage = lastMessages.get(topic);
        if(lastMessage != null)
        {
            messagingTemplate.convertAndSend(topic, lastMessage);
        }
    }
}
