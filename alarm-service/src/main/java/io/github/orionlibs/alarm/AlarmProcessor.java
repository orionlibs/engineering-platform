package io.github.orionlibs.alarm;

import io.github.orionlibs.alarm.model.AlarmEventModel;
import io.github.orionlibs.alarm.model.AlarmModel;
import io.github.orionlibs.core.event.EventData;
import io.github.orionlibs.core.event.EventSubscriber;
import io.github.orionlibs.core.json.JSONService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlarmProcessor implements EventSubscriber
{
    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private AlarmService alarmService;
    @Autowired private AlarmEventService alarmEventService;


    public AlarmProcessor()
    {
        //EventManager.registerEventListener("TagUpdate", this);
    }


    @Override
    public void subscribe(String topic)
    {
    }


    @Override
    public void process(EventData event)
    {
        String tagID = (String)event.getEventParameters().get("tagID");
        List<AlarmModel> alarms = alarmService.getEnabledAlarmsByTagID(tagID);
        for(AlarmModel alarm : alarms)
        {
            String alarmMessage = "";
            ValueConditionMode condition = ValueConditionMode.getEnumForValue(Integer.toString(alarm.getValueConditionMode()));
            boolean notifyWebsocket = false;
            String alarmEventID = null;
            if(condition == ValueConditionMode.EQUALS)
            {
                if(event.getEventParameters().get("tagValue").equals(alarm.getStringSetpoint()))
                {
                    alarmMessage = "The tag \"" + event.getEventParameters().get("tagPath") + "\" has the setpoint value of: \"" + alarm.getStringSetpoint() + "\"";
                    AlarmEventModel alarmEventModel = new AlarmEventModel();
                    alarmEventModel.setAlarm(alarm);
                    alarmEventModel.setMessage(alarmMessage);
                    alarmEventModel.setAcknowledged(false);
                    alarmEventModel.setTagID(alarm.getTagID());
                    alarmEventModel = alarmEventService.save(alarmEventModel);
                    alarmEventID = alarmEventModel.getId().toString();
                    notifyWebsocket = true;
                }
                else
                {
                    notifyWebsocket = false;
                }
            }
            else if(condition == ValueConditionMode.NOT_EQUALS || condition == ValueConditionMode.ANY_CHANGE)
            {
                if(!event.getEventParameters().get("tagValue").equals(alarm.getStringSetpoint()))
                {
                    alarmMessage = "The tag \"" + event.getEventParameters().get("tagPath") + "\" has value: \"" + event.getEventParameters().get("tagValue") + "\" different than the setpoint value of: \"" + alarm.getStringSetpoint() + "\"";
                    AlarmEventModel alarmEventModel = new AlarmEventModel();
                    alarmEventModel.setAlarm(alarm);
                    alarmEventModel.setMessage(alarmMessage);
                    alarmEventModel.setAcknowledged(false);
                    alarmEventModel.setTagID(alarm.getTagID());
                    alarmEventModel = alarmEventService.save(alarmEventModel);
                    alarmEventID = alarmEventModel.getId().toString();
                    notifyWebsocket = true;
                }
                else
                {
                    notifyWebsocket = false;
                }
            }
            if(notifyWebsocket)
            {
                String message = JSONService.convertObjectToJSON(AlarmNotification.builder()
                                .alarmID(alarm.getId().toString())
                                .alarmEventID(alarmEventID)
                                .alarmMessage(alarmMessage)
                                .build());
                //DatabaseConnectivityWebsocketController.lastMessages.put("/topic/alarms", message);
                messagingTemplate.convertAndSend("/topic/alarms", message);
            }
        }
    }
}
