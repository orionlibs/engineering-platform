package io.github.orionlibs.core.tests;

import io.github.orionlibs.core.Logger;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Component
public class WebsocketTestUtils implements JWTBuilderForTests
{
    @Value("${jwt.secret}")
    private String base64Secret;
    public WebSocketStompClient webSocketStompClient;
    public StompSession stompSession;
    public CompletableFuture<Object> received;
    private ThreadPoolTaskScheduler taskScheduler;


    public void setupWebsocket(int port,
                    String subject,
                    String commaSeparatedAuthorities,
                    String topic,
                    Class<?> websocketMessageDataType)
                    throws ExecutionException, InterruptedException, TimeoutException
    {
        Logger.info("[JUnit] setting up websocket (connect + subscribe)");
        List<Transport> transports = List.of(
                        new WebSocketTransport(new StandardWebSocketClient()),
                        new RestTemplateXhrTransport(new RestTemplate())
        );
        SockJsClient sockJsClient = new SockJsClient(transports);
        webSocketStompClient = new WebSocketStompClient(sockJsClient);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("ws-stomp-scheduler-");
        taskScheduler.initialize();
        webSocketStompClient.setTaskScheduler(taskScheduler);
        webSocketStompClient.setReceiptTimeLimit((int)Duration.ofSeconds(5).toMillis());
        // build, sign token
        WebSocketHttpHeaders connectHeaders = new WebSocketHttpHeaders();
        connectHeaders.add(HttpHeaders.AUTHORIZATION,
                        "Bearer " + jwtWithAuthorities(base64Secret, subject, commaSeparatedAuthorities.split(",")));
        String url = "http://localhost:" + port + "/websocket"; // matches your server config
        // we need a session handler that captures receipts for subscribe acknowledgements
        final CompletableFuture<Void> connected = new CompletableFuture<>();
        final CompletableFuture<String> receiptFuture = new CompletableFuture<>();
        StompSessionHandlerAdapter handler = new StompSessionHandlerAdapter()
        {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders)
            {
                connected.complete(null);
            }


            /*@Override
            public void handleReceipt(StompSession session, StompHeaders stompHeaders)
            {
                // server responds with header "receipt-id" set to the value we sent
                String receiptId = stompHeaders.getFirst("receipt-id");
                if(receiptId == null)
                {
                    receiptId = stompHeaders.getFirst("receipt");
                }
                receiptFuture.complete(receiptId);
            }*/


            @Override
            public void handleFrame(StompHeaders headers, Object payload)
            {
                // fallback
            }


            @Override
            public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex)
            {
                // surface exception to test if needed
                receiptFuture.completeExceptionally(ex);
                connected.completeExceptionally(ex);
            }
        };
        stompSession = webSocketStompClient.connectAsync(url, connectHeaders, handler)
                        .get(5, TimeUnit.SECONDS);
        // ensure afterConnected fired
        connected.get(5, TimeUnit.SECONDS);
        // subscribe with a receipt header so we get acknowledgement from server
        StompHeaders subscribeHeaders = new StompHeaders();
        subscribeHeaders.setDestination(topic);
        String receiptId = "receipt-" + java.util.UUID.randomUUID();
        subscribeHeaders.setReceipt(receiptId);
        stompSession.subscribe(subscribeHeaders, new StompFrameHandler()
        {
            @Override
            public Type getPayloadType(StompHeaders headers)
            {
                return websocketMessageDataType;
            }


            @Override
            public void handleFrame(StompHeaders headers, Object payload)
            {
                received.complete(payload);
            }
        });
        // Wait for server to acknowledge the SUBSCRIBE (via RECEIPT)
        String ack = receiptFuture.get(5, TimeUnit.SECONDS);
        if(!receiptId.equals(ack) && ack != null)
        {
            // some brokers set different header names; just log for debugging
            Logger.info("[JUnit] subscribe receipt ack: " + ack + " (expected " + receiptId + ")");
        }
    }


    public void shutdownWebsocketClient()
    {
        if(webSocketStompClient != null)
        {
            webSocketStompClient.stop();
        }
        if(taskScheduler != null)
        {
            try
            {
                taskScheduler.shutdown();
            }
            catch(Exception ignored)
            {
            }
        }
    }
}
