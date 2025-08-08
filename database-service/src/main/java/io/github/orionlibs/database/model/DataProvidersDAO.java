package io.github.orionlibs.database.model;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataProvidersDAO extends JpaRepository<DataProviderModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
