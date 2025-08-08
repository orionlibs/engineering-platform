package io.github.orionlibs.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "data_providers", schema = "uns", indexes = {
                @Index(name = "idx_uns_data_providers", columnList = "configuration_key")
})
public class DataProviderModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "connection_url", nullable = false)
    private String connectionURL;
    @Column(nullable = true)
    private String username;
    @Column(nullable = true)
    private String password;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public DataProviderModel()
    {
    }


    public DataProviderModel(String connectionURL, String username, String password)
    {
        this.connectionURL = connectionURL;
        this.username = username;
        this.password = password;
    }


    public UUID getId()
    {
        return id;
    }


    public void setId(UUID id)
    {
        this.id = id;
    }


    public String getConnectionURL()
    {
        return connectionURL;
    }


    public void setConnectionURL(String connectionURL)
    {
        this.connectionURL = connectionURL;
    }


    public String getUsername()
    {
        return username;
    }


    public void setUsername(String username)
    {
        this.username = username;
    }


    public String getPassword()
    {
        return password;
    }


    public void setPassword(String password)
    {
        this.password = password;
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
}
