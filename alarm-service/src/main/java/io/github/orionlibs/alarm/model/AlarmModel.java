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
@Table(name = "alarms", schema = "uns", indexes = {
                @Index(name = "idx_uns_alarms", columnList = "id")
})
public class AlarmModel implements OrionModel, Comparable<AlarmModel>
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "string_setpoint")
    private String stringSetpoint;
    @Column(name = "numerical_setpoint")
    private Number numericalSetpoint;
    @Column(name = "value_condition_mode")
    private int valueConditionMode;
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


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public boolean isEnabled()
    {
        return isEnabled;
    }


    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }


    public String getStringSetpoint()
    {
        return stringSetpoint;
    }


    public void setStringSetpoint(String stringSetpoint)
    {
        this.stringSetpoint = stringSetpoint;
    }


    public Number getNumericalSetpoint()
    {
        return numericalSetpoint;
    }


    public void setNumericalSetpoint(Number numericalSetpoint)
    {
        this.numericalSetpoint = numericalSetpoint;
    }


    public int getValueConditionMode()
    {
        return valueConditionMode;
    }


    public void setValueConditionMode(int valueConditionMode)
    {
        this.valueConditionMode = valueConditionMode;
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
        else if(other instanceof AlarmModel)
        {
            AlarmModel otherTemp = (AlarmModel)other;
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


    @Override
    public int compareTo(AlarmModel other)
    {
        if(other == null)
        {
            return -1;
        }
        else
        {
            return name.compareTo(other.name);
        }
    }
}
