package io.github.orionlibs.user.password.forgot.model;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRequestsDAO extends JpaRepository<ForgotPasswordRequestModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<ForgotPasswordRequestModel> findByForgotPasswordCode(String forgotPasswordCode);
}
