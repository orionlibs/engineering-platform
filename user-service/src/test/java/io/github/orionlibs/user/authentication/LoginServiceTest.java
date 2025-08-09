package io.github.orionlibs.user.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.user.authentication.api.LoginRequest;
import io.github.orionlibs.core.user.registration.UserRegistrationService;
import io.github.orionlibs.core.user.registration.api.UserRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class LoginServiceTest
{
    @Autowired UserDAO dao;
    @Autowired UserRegistrationService userRegistrationService;
    @Autowired LoginService loginService;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void loginUser()
    {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .authority("USER")
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build();
        userRegistrationService.registerUser(request);
        LoginRequest request2 = LoginRequest.builder()
                        .username("me@email.com")
                        .password("bunkzh3Z!")
                        .build();
        String token = loginService.loginUserAndGetToken(request2);
        assertThat(token.length()).isGreaterThan(10);
    }
}
