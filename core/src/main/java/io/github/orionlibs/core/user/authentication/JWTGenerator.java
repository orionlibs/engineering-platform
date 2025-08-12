package io.github.orionlibs.core.user.authentication;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator
{
    private static final long EXPIRATION_IN_MILLISECONDS = 3_600_000L;
    @Autowired private UserService userService;
    @Autowired private HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;
    @Autowired private JWTSigningKeyToSecretKeyConverter signingKeyToSecretKeyConverter;


    public String generateToken(String userID, Collection<? extends GrantedAuthority> authorities)
    {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_IN_MILLISECONDS);
        String token = Jwts.builder()
                        .setSubject(userID)
                        .claim("authorities", authorities
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList())
                        .issuedAt(now)
                        .expiration(expirationDate)
                        .signWith(signingKeyToSecretKeyConverter.convert(hmacSHAEncryptionKeyProvider.getJwtSigningKey()), SignatureAlgorithm.HS512)
                        .compact();
        return token;
    }


    /*public String generateToken(UserDetails userDetails)
    {
        UserModel user = userService.loadUserAsModelByUsername(userDetails.getUsername());
        String token = Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("authorities", userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_IN_MILLISECONDS))
                        .signWith(signingKeyToSecretKeyConverter.convert(hmacSHAEncryptionKeyProvider.getJwtSigningKey()), SignatureAlgorithm.HS512)
                        .compact();
        return token;
    }*/


    /*public String generateToken(UserDetails userDetails, Date issuedAt, Date expiresAt)
    {
        UserModel user = userService.loadUserAsModelByUsername(userDetails.getUsername());
        String token = Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("authorities", userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList())
                        .issuedAt(issuedAt)
                        .expiration(expiresAt)
                        .signWith(signingKeyToSecretKeyConverter.convert(hmacSHAEncryptionKeyProvider.getJwtSigningKey()), SignatureAlgorithm.HS512)
                        .compact();
        return token;
    }*/
}
