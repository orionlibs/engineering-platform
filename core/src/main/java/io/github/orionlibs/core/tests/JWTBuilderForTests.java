package io.github.orionlibs.core.tests;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;

public interface JWTBuilderForTests extends JWTSigningKeyForTests
{
    default String jwtWithAuthorities(String base64Secret, String subject, String... authorities)
    {
        String apiKey = Jwts.builder()
                        .setSubject(subject)
                        .subject(subject)
                        .claim("authorities", List.of(authorities))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
                        .signWith(getSigningKey(base64Secret), SignatureAlgorithm.HS512)
                        .compact();
        return apiKey;
    }
}
