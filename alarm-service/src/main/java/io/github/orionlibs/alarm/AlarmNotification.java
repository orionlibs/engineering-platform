package io.github.orionlibs.alarm;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AlarmNotification implements Serializable
{
    private String alarmID;
    private String alarmEventID;
    private String alarmMessage;
}
