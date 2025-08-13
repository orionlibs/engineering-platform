package io.github.orionlibs.core.api.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record APIField(String field,
                       String message,
                       @JsonProperty("rejected_value") Object rejectedValue)
{
}
