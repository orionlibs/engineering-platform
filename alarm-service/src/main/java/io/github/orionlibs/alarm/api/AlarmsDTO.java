package io.github.orionlibs.alarm.api;

import java.io.Serializable;
import java.util.List;

public record AlarmsDTO(List<AlarmDTO> alarms) implements Serializable
{
}
