package io.github.orionlibs.core.user.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JWTSigningKeyToSecretKeyConverterTest
{
    static final String RAW_SIGNING_KEY = "0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF";
    static final String SIGNING_KEY_BASE64 = Base64.getEncoder().encodeToString(RAW_SIGNING_KEY.getBytes(StandardCharsets.UTF_8));
    @Autowired JWTSigningKeyToSecretKeyConverter signingKeyToSecretKeyConverter;


    @Test
    void convertSigningKeyToSecretKeyObject()
    {
        Key key = signingKeyToSecretKeyConverter.convert(SIGNING_KEY_BASE64);
        assertThat(key).isNotNull();
        assertThat(key).isInstanceOf(SecretKey.class);
        assertThat(key.getAlgorithm()).isEqualTo(SignatureAlgorithm.HS512.getJcaName());
    }
}
