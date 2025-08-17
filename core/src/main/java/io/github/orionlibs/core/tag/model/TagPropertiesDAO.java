package io.github.orionlibs.core.tag.model;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagPropertiesDAO extends JpaRepository<TagPropertyModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    List<TagPropertyModel> findAllByTag_Id(UUID tagId);


    List<TagPropertyModel> findAllByTagByPath_Path(String path);
}
