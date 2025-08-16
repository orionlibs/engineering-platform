package io.github.orionlibs.alarm;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.alarm.model.AlarmModel;
import io.github.orionlibs.core.event.EventData;
import io.github.orionlibs.core.tests.WebsocketTestUtils;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AlarmProcessorTest
{
    @LocalServerPort int port;
    @Autowired AlarmService alarmService;
    @Autowired AlarmProcessor alarmProcessor;
    @Autowired WebsocketTestUtils websocketTestUtils;


    @BeforeEach
    void setup() throws ExecutionException, InterruptedException, TimeoutException
    {
        alarmService.deleteAll();
        websocketTestUtils.setupWebsocket(port, UUID.randomUUID().toString(), "USER", "/topic/alarms", AlarmNotification.class);
    }


    @AfterEach
    void teardownClient()
    {
        if(websocketTestUtils.webSocketStompClient != null)
        {
            websocketTestUtils.webSocketStompClient.stop();
        }
    }


    @Test
    void process() throws ExecutionException, InterruptedException, TimeoutException
    {
        AlarmModel model1 = new AlarmModel();
        model1.setName("alarm1");
        model1.setDescription("desc1");
        model1.setTagID("tag-id-1");
        model1.setEnabled(true);
        model1.setStringSetpoint("setpoint1");
        model1.setValueConditionMode(ValueConditionMode.EQUALS.getAsInt());
        model1 = alarmService.save(model1);
        EventData eventData = new EventData("event-type", "some-message", Map.of("tagID", "tag-id-1",
                        "tagValue", "setpoint1"));
        alarmProcessor.process(eventData);
        AlarmNotification g = (AlarmNotification)websocketTestUtils.received.get(5, TimeUnit.SECONDS);
        assertThat(g).isNotNull();
        assertThat(g.getAlarmMessage()).isEqualTo("some-message");
        assertThat(g.getAlarmID()).isEqualTo(model1.getId().toString());
        assertThat(g.getAlarmEventID().length()).isGreaterThan(20);
        websocketTestUtils.stompSession.disconnect();
    }
}
