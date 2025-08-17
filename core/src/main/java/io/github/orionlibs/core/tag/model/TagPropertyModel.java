package io.github.orionlibs.core.tag.model;

import io.github.orionlibs.core.database.OrionModel;
import io.github.orionlibs.core.tag.DataType;
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
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tag_properties", schema = "uns", indexes = {
                @Index(name = "idx_uns_tag_properties", columnList = "id,tag_id")
})
public class TagPropertyModel implements OrionModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(
                    name = "tag_id",
                    nullable = false,
                    foreignKey = @ForeignKey(
                                    name = "fk_tags_id",
                                    foreignKeyDefinition =
                                                    "FOREIGN KEY (tag_id) REFERENCES uns.tags(id) ON DELETE CASCADE"
                    )
    )
    private TagModel tag;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(
                    name = "tag_path",
                    referencedColumnName = "path",
                    nullable = true,
                    foreignKey = @ForeignKey(
                                    name = "fk_tags_path",
                                    foreignKeyDefinition = "FOREIGN KEY (tag_path) REFERENCES uns.tags(path) ON DELETE NO ACTION"
                    )
    )
    private TagModel tagByPath;
    @Column(name = "property_name", nullable = false)
    private String name;
    @Column(name = "property_value", nullable = false)
    private String value;
    @Column(name = "data_type", nullable = false)
    private int dataType;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public DataType getPropertyDataType()
    {
        return DataType.getEnumForValue(Integer.toString(dataType));
    }


    public UUID getId()
    {
        return id;
    }


    public void setId(UUID id)
    {
        this.id = id;
    }


    public TagModel getTag()
    {
        return tag;
    }


    public void setTag(TagModel tag)
    {
        this.tag = tag;
    }


    public TagModel getTagByPath()
    {
        return tagByPath;
    }


    public void setTagByPath(TagModel tagByPath)
    {
        this.tagByPath = tagByPath;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getValue()
    {
        return value;
    }


    public void setValue(String value)
    {
        this.value = value;
    }


    public int getDataType()
    {
        return dataType;
    }


    public void setDataType(int dataType)
    {
        this.dataType = dataType;
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
