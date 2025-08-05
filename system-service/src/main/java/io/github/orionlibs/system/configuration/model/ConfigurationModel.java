package io.github.orionlibs.system.configuration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "configuration", schema = "uns", indexes = {
                @Index(name = "idx_uns_configuration", columnList = "configuration_key")
})
public class ConfigurationModel implements Comparable<ConfigurationModel>
{
    @Id
    @Column(name = "configuration_key", updatable = false, unique = true, nullable = false)
    private String key;
    @Column(name = "configuration_value", nullable = false)
    private String value;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public ConfigurationModel()
    {
    }


    public ConfigurationModel(String key, String value)
    {
        this.key = key;
        this.value = value;
    }


    public String getKey()
    {
        return key;
    }


    public void setKey(String key)
    {
        this.key = key;
    }


    public String getValue()
    {
        return value;
    }


    public void setValue(String value)
    {
        this.value = value;
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
    public int compareTo(ConfigurationModel other)
    {
        if(other == null)
        {
            return 1;
        }
        return this.key.compareTo(other.key);
    }


    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof ConfigurationModel))
        {
            return false;
        }
        ConfigurationModel that = (ConfigurationModel)o;
        return Objects.equals(key, that.key);
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(key);
    }
}
