package io.github.orionlibs.user;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.api.UpdatePasswordRequest;
import io.github.orionlibs.user.registration.UserRegistrationService;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PasswordServiceTest
{
    @Autowired UserService userService;
    @Autowired UserRegistrationService userRegistrationService;
    @Autowired PasswordService passwordService;
    UserModel user;


    @BeforeEach
    void setup()
    {
        userService.deleteAll();
        user = userRegistrationService.registerUser(UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority("USER")
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build());
    }


    @Test
    void updatePassword()
    {
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                        .password("bunkzh3Z!1")
                        .build();
        boolean result = passwordService.update(user.getId().toString(), request);
        assertThat(result).isTrue();
    }


    @Test
    void updatePassword_wrongUser()
    {
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                        .password("bunkzh3Z!1")
                        .build();
        boolean result = passwordService.update(UUID.randomUUID().toString(), request);
        assertThat(result).isFalse();
    }
}
