package io.github.orionlibs.alarm.model;

import io.github.orionlibs.core.database.OrionModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "alarm_events", schema = "uns", indexes = {
                @Index(name = "idx_uns_alarm_events", columnList = "id")
})
public class AlarmEventModel implements OrionModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(
                    name = "alarm_id",
                    nullable = false,
                    foreignKey = @ForeignKey(
                                    name = "fk_alarm_events_alarm",
                                    foreignKeyDefinition =
                                                    "FOREIGN KEY (alarm_id) REFERENCES uns.alarms(id) ON DELETE CASCADE"
                    )
    )
    private AlarmModel alarm;
    @Column(name = "tag_id")
    private String tagID;
    @Column(name = "is_acknowledged")
    private boolean isAcknowledged;
    @Column
    private String message;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UUID getId()
    {
        return id;
    }


    public void setId(UUID id)
    {
        this.id = id;
    }


    public AlarmModel getAlarm()
    {
        return alarm;
    }


    public void setAlarm(AlarmModel alarm)
    {
        this.alarm = alarm;
    }


    public String getTagID()
    {
        return tagID;
    }


    public void setTagID(String tagID)
    {
        this.tagID = tagID;
    }


    public boolean isAcknowledged()
    {
        return isAcknowledged;
    }


    public void setAcknowledged(boolean acknowledged)
    {
        isAcknowledged = acknowledged;
    }


    public String getMessage()
    {
        return message;
    }


    public void setMessage(String message)
    {
        this.message = message;
    }


    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }


    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }


    @Override
    public boolean equals(Object other)
    {
        if(this == other)
        {
            return true;
        }
        else if(other instanceof AlarmEventModel)
        {
            AlarmEventModel otherTemp = (AlarmEventModel)other;
            return Objects.equals(id, otherTemp.getId());
        }
        else
        {
            return false;
        }
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
