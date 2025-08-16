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
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
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


    public void setupWebsocket(int port, String subject, String commaSeparatedAuthorities, String topic, Class<?> websocketMessageDataType) throws ExecutionException, InterruptedException, TimeoutException
    {
        Logger.info("[JUnit] setting up websocket");
        //webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        //webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        //webSocketStompClient.setReceiptTimeLimit((int)Duration.ofSeconds(5).toMillis());
        List<Transport> transports = List.of(
                        new WebSocketTransport(new StandardWebSocketClient()),
                        new RestTemplateXhrTransport(new RestTemplate())
        );
        SockJsClient sockJsClient = new SockJsClient(transports);
        webSocketStompClient = new WebSocketStompClient(sockJsClient);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
        webSocketStompClient.setReceiptTimeLimit((int)Duration.ofSeconds(5).toMillis());
        WebSocketHttpHeaders connectHeaders = new WebSocketHttpHeaders();
        connectHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtWithAuthorities(base64Secret, subject, commaSeparatedAuthorities.split(",")));
        String url = "http://localhost:" + port + "/websocket";
        received = new CompletableFuture<>();
        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter()
        {
        };
        stompSession = webSocketStompClient.connectAsync(url, connectHeaders, sessionHandler).get(5, TimeUnit.SECONDS);
        stompSession.subscribe(topic, new StompFrameHandler()
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
    }
}
