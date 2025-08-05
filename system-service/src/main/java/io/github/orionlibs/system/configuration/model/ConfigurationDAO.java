package io.github.orionlibs.system.configuration.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationDAO extends JpaRepository<ConfigurationModel, String>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<ConfigurationModel> findByKey(String key);


    boolean existsByKey(String key);
}
