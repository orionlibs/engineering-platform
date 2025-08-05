package io.github.orionlibs.user;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.api.CreateForgotPasswordRequestRequest;
import io.github.orionlibs.user.registration.UserRegistrationService;
import io.github.orionlibs.user.registration.api.UserRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ForgotPasswordServiceTest
{
    @Autowired UserService userService;
    @Autowired UserRegistrationService userRegistrationService;
    @Autowired ForgotPasswordService forgotPasswordService;
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
    void process_userNotFound()
    {
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me111111@email.com")
                        .build();
        boolean result = forgotPasswordService.process(request);
        assertThat(result).isFalse();
    }


    @Test
    void process()
    {
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me@email.com")
                        .build();
        boolean result = forgotPasswordService.process(request);
        assertThat(result).isTrue();
    }
}
