package io.github.orionlibs.alarm.model;

import io.github.orionlibs.core.database.OrionModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "alarm_event_data", schema = "uns", indexes = {
                @Index(name = "idx_uns_alarm_event_data", columnList = "id")
})
public class AlarmEventDataModel implements OrionModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "property_name", nullable = false)
    private String propertyName;
    @Column(name = "property_value", nullable = false)
    private String propertyValue;
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


    public String getPropertyName()
    {
        return propertyName;
    }


    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }


    public String getPropertyValue()
    {
        return propertyValue;
    }


    public void setPropertyValue(String propertyValue)
    {
        this.propertyValue = propertyValue;
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
        else if(other instanceof AlarmEventDataModel)
        {
            AlarmEventDataModel otherTemp = (AlarmEventDataModel)other;
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
