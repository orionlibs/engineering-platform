package io.github.orionlibs.user;

import io.github.orionlibs.core.json.JSONService;
import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.user.api.key.APIKeyService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAPITestUtils extends APITestUtils
{
    @Autowired JSONService jsonService;
    @Autowired APIKeyService apiKeyService;
    @Value("${jwt.secret}")
    String base64Secret;


    private Key getSigningKey()
    {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }


    @Override
    protected String jwtWithAuthorities(String subject, String... authorities)
    {
        String apiKey = Jwts.builder()
                        .setSubject(subject)
                        .subject(subject)
                        .claim("authorities", List.of(authorities))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
                        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                        .compact();
        apiKeyService.save(subject, apiKey, "");
        return apiKey;
    }
}
