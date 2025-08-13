package io.github.orionlibs.core.user.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserModel;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JWTGeneratorTest
{
    static final String RAW_SIGNING_KEY = "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF";
    static final String SIGNING_KEY_BASE64 = Base64.getEncoder().encodeToString(RAW_SIGNING_KEY.getBytes(StandardCharsets.UTF_8));
    @Autowired UserService userService;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;
    @Autowired JWTGenerator jwtGenerator;
    UserModel user;


    @BeforeEach
    void setup()
    {
        userService.deleteAll();
        UserModel newUser = new UserModel(hmacSHAEncryptionKeyProvider);
        newUser.setUsername("me@email.com");
        newUser.setPassword("4528");
        newUser.setAuthority("USER");
        newUser.setFirstName("Jimmy");
        newUser.setLastName("Emilson");
        newUser.setPhoneNumber("07896620211");
        user = userService.saveUser(newUser);
    }


    @Test
    void generateToken_extractUserID()
    {
        String token = jwtGenerator.generateToken(user.getId().toString(), user.getAuthorities());
        assertThat(token.length()).isGreaterThan(20);
    }
}
