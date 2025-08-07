package io.github.orionlibs.user.password.forgot.api;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateForgotPasswordRequestRequest
{
    @Email(message = "Invalid email address format")
    private String username;
}
