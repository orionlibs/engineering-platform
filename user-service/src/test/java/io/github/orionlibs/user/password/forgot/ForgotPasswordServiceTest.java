package io.github.orionlibs.user.password.forgot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.password.forgot.api.CreateForgotPasswordRequestRequest;
import io.github.orionlibs.core.user.registration.UserRegistrationService;
import io.github.orionlibs.core.user.registration.api.UserRegistrationRequest;
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


    @Test
    void isCodeValid()
    {
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me@email.com")
                        .build();
        boolean result = forgotPasswordService.process(request);
        result = forgotPasswordService.isCodeValid(request.getForgotPasswordCodeGenerated());
        assertThat(result).isTrue();
        result = forgotPasswordService.isCodeValid("nonexistentcode");
        assertThat(result).isFalse();
    }


    @Test
    void getUserIDByForgotPasswordCode()
    {
        CreateForgotPasswordRequestRequest request = CreateForgotPasswordRequestRequest.builder()
                        .username("me@email.com")
                        .build();
        boolean result = forgotPasswordService.process(request);
        String userID = forgotPasswordService.getUserIDByForgotPasswordCode(request.getForgotPasswordCodeGenerated());
        assertThat(user.getId().toString()).isEqualTo(userID);
    }
}
