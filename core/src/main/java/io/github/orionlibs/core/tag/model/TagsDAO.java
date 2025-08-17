package io.github.orionlibs.core.tag.model;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsDAO extends JpaRepository<TagModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<TagModel> findByPath(String path);


    @Query("SELECT c.id FROM TagModel c WHERE c.path = :path")
    UUID findIDByPath(@Param("path") String path);


    @Query("SELECT c.path FROM TagModel c WHERE c.id = :id")
    String findPathByID(@Param("id") UUID id);
}
