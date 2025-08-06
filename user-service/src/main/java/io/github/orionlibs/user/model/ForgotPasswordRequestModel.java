package io.github.orionlibs.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "forgot_password_requests", schema = "uns", indexes = {
                @Index(name = "idx_uns_forgot_password_requests", columnList = "forgot_password_code")
})
public class ForgotPasswordRequestModel
{
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userID;
    @Column(name = "forgot_password_code", nullable = false)
    private String forgotPasswordCode;
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public UUID getUserID()
    {
        return userID;
    }


    public void setUserID(UUID userID)
    {
        this.userID = userID;
    }


    public String getForgotPasswordCode()
    {
        return forgotPasswordCode;
    }


    public void setForgotPasswordCode(String forgotPasswordCode)
    {
        this.forgotPasswordCode = forgotPasswordCode;
    }


    public LocalDateTime getExpiresAt()
    {
        return expiresAt;
    }


    public void setExpiresAt(LocalDateTime expiresAt)
    {
        this.expiresAt = expiresAt;
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
