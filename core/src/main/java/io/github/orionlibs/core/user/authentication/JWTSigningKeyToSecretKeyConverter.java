package io.github.orionlibs.core.user.authentication;

import io.github.orionlibs.core.Converter;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class JWTSigningKeyToSecretKeyConverter implements Converter<String, Key>
{
    @Override
    public Key convert(String signingKey)
    {
        return new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
    }
}
