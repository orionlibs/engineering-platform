package io.github.orionlibs.database.connectivity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class DatabaseConnectivityStatus
{
    private String databaseName;
    private String connectionURL;
    private Boolean isConnected;
    private LocalDateTime datetimeConnectionEstablished;
    private LocalDateTime datetimeConnectionLost;
    private String errorMessage;
}
