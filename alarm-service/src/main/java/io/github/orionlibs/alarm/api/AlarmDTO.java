package io.github.orionlibs.alarm.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record AlarmDTO(String id,
                       String name,
                       @JsonProperty("device_description") String description,
                       @JsonProperty("tag_id") String tagID,
                       @JsonProperty("is_enabled") boolean isEnabled,
                       String setpoint,
                       @JsonProperty("value_condition_mode") String valueConditionMode) implements Serializable
{
}
