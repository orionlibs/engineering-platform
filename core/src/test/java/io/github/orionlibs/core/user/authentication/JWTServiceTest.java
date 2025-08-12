package io.github.orionlibs.core.user.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserModel;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JWTServiceTest
{
    static final String RAW_SIGNING_KEY = "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF";
    static final String SIGNING_KEY_BASE64 = Base64.getEncoder().encodeToString(RAW_SIGNING_KEY.getBytes(StandardCharsets.UTF_8));
    @Autowired JWTService jwtService;
    @Autowired UserService userService;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;
    @Autowired JWTSigningKeyToSecretKeyConverter signingKeyToSecretKeyConverter;
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
    void convertSigningKeyToSecretKeyObject()
    {
        Key key = signingKeyToSecretKeyConverter.convert(SIGNING_KEY_BASE64);
        assertThat(key).isNotNull();
        assertThat(key).isInstanceOf(SecretKey.class);
        assertThat(key.getAlgorithm()).isEqualTo(SignatureAlgorithm.HS512.getJcaName());
    }


    @Test
    void generateToken_extractUserID()
    {
        String token = jwtGenerator.generateToken(user.getId().toString(), user.getAuthorities());
        assertThat(token).isNotNull();
        String extracted = jwtService.extractUserID(token);
        assertThat(extracted.length()).isGreaterThan(20);
    }


    /*@Test
    void isToken_expiredTokenValid()
    {
        Date now = new Date();
        Date past = new Date(now.getTime() - 10_000);
        UserDetails user = new User(
                        "me@email.com",
                        "4528",
                        List.of(new SimpleGrantedAuthority("USER"))
        );
        String expiredToken = jwtGenerator.generateToken(user, now, past);
        assertThat(expiredToken).isNotNull();
        assertThat(jwtService.isTokenValid(expiredToken, user)).isFalse();
    }*/
}
