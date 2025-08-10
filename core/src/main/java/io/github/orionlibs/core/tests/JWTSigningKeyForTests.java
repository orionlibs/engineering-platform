package io.github.orionlibs.core.tests;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

public interface JWTSigningKeyForTests
{
    default Key getSigningKey(String base64Secret)
    {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }
}
