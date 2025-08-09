package io.github.orionlibs.core.tests;

import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.core.user.registration.UserRegistrationService;
import io.github.orionlibs.core.user.registration.api.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestUtils
{
    @Autowired UserRegistrationService userRegistrationService;


    public UserModel registerUser(String username, String authority)
    {
        return userRegistrationService.registerUser(UserRegistrationRequest.builder()
                        .username(username)
                        .password("bunkzh3Z!")
                        .authority(authority)
                        .firstName("Dimi")
                        .lastName("Emilson")
                        .phoneNumber("07896620211")
                        .build());
    }
}
