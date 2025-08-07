package io.github.orionlibs.user.password.forgot.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRequestsDAO extends JpaRepository<ForgotPasswordRequestModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<ForgotPasswordRequestModel> findByForgotPasswordCode(String forgotPasswordCode);


    void deleteByForgotPasswordCode(String forgotPasswordCode);


    @Query("SELECT c.userID FROM ForgotPasswordRequestModel c WHERE c.forgotPasswordCode = :forgot_password_code")
    String findUsesrIDByForgotPasswordCode(@Param("forgot_password_code") String forgotPasswordCode);


    long deleteByExpiresAtBefore(LocalDateTime cutoff);
}
