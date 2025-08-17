package io.github.orionlibs.core.tag.model;

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
@Table(name = "tags", schema = "uns", indexes = {
                @Index(name = "idx_uns_tags", columnList = "id,path")
})
public class TagModel implements OrionModel, Comparable<TagModel>
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String path;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public String getPath()
    {
        return path;
    }


    public void setPath(String path)
    {
        this.path = path;
    }


    public UUID getId()
    {
        return id;
    }


    public void setId(UUID id)
    {
        this.id = id;
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
        else if(other instanceof TagModel)
        {
            TagModel otherTemp = (TagModel)other;
            return Objects.equals(path, otherTemp.getPath());
        }
        else
        {
            return false;
        }
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(path);
    }


    @Override
    public int compareTo(TagModel other)
    {
        if(other == null)
        {
            return -1;
        }
        else
        {
            return path.compareTo(other.path);
        }
    }
}
