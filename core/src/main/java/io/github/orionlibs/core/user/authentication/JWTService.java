package io.github.orionlibs.core.user.authentication;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.UserAuthority;
import io.github.orionlibs.core.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JWTService
{
    private static final long EXPIRATION_IN_MILLISECONDS = 3_600_000L;
    @Autowired private UserService userService;
    @Autowired private HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;
    @Autowired private JWTSigningKeyToSecretKeyConverter signingKeyToSecretKeyConverter;


    public boolean isTokenValid(String token)
    {
        String userID = extractUserID(token);
        boolean isTokenExpired = false;
        try
        {
            isTokenExpired = isTokenExpired(token);
        }
        catch(ExpiredJwtException e)
        {
            isTokenExpired = true;
        }
        catch(Exception e)
        {
            isTokenExpired = false;
        }
        return !isTokenExpired;
    }


    /*public boolean isTokenValid(String token, UserModel userDetails)
    {
        String userID = extractUserID(token);
        UserModel user = userService.loadUserAsModelByUsername(userDetails.getUsername());
        boolean isTokenExpired = false;
        try
        {
            isTokenExpired = isTokenExpired(token);
        }
        catch(ExpiredJwtException e)
        {
            isTokenExpired = true;
        }
        catch(Exception e)
        {
            isTokenExpired = false;
        }
        return userID.equals(user.getId().toString()) && !isTokenExpired;
    }*/


    /*public boolean isTokenValid(String token, UserDetails userDetails)
    {
        String userID = extractUserID(token);
        UserModel user = userService.loadUserAsModelByUsername(userDetails.getUsername());
        boolean isTokenExpired = false;
        try
        {
            isTokenExpired = isTokenExpired(token);
        }
        catch(ExpiredJwtException e)
        {
            isTokenExpired = true;
        }
        catch(Exception e)
        {
            isTokenExpired = false;
        }
        return userID.equals(user.getId().toString()) && !isTokenExpired;
    }*/


    public String extractUserID(String token)
    {
        boolean isTokenExpired = false;
        try
        {
            isTokenExpired = isTokenExpired(token);
        }
        catch(ExpiredJwtException e)
        {
            isTokenExpired = true;
        }
        catch(Exception e)
        {
            isTokenExpired = false;
        }
        if(isTokenExpired)
        {
            return "INVALID-USER-ID";
        }
        else
        {
            Claims claims = parseClaims(token);
            return claims.getSubject();
        }
    }


    private boolean isTokenExpired(String token)
    {
        try
        {
            Claims claims = parseClaims(token);
            return claims.getExpiration().before(new Date());
        }
        catch(ExpiredJwtException e)
        {
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }


    private Claims parseClaims(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException
    {
        SecretKey key = (SecretKey)signingKeyToSecretKeyConverter.convert(hmacSHAEncryptionKeyProvider.getJwtSigningKey());
        return Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseClaimsJws(token)
                        .getPayload();
    }


    /*ExpiredJwtException
    UnsupportedJwtException
    MalformedJwtException
    SignatureException
    SecurityException
    IllegalArgumentException*/
    public JWTToken getJWTTokenData(String token)
    {
        JWTToken jwtToken = new JWTToken();
        jwtToken.setToken(token);
        jwtToken.setUserID(UserAuthority.ANONYMOUS.name());
        try
        {
            Claims claims = parseClaims(token);
            getDataFromToken(jwtToken, claims);
        }
        catch(SecurityException ex)
        {
            jwtToken.setHasErrors(true);
        }
        catch(MalformedJwtException ex)
        {
            jwtToken.setHasErrors(true);
        }
        catch(ExpiredJwtException ex)
        {
            jwtToken.setHasErrors(true);
            jwtToken.setExpiredToken(true);
            getDataFromToken(jwtToken, ex.getClaims());
        }
        catch(UnsupportedJwtException ex)
        {
            jwtToken.setHasErrors(true);
        }
        catch(IllegalArgumentException ex)
        {
            jwtToken.setHasErrors(true);
        }
        return jwtToken;
    }


    @SuppressWarnings("unchecked")
    private void getDataFromToken(JWTToken jwtToken, Claims claims)
    {
        String subject = claims.getSubject();
        String userID = subject;
        jwtToken.setUserID(userID);
        List<String> authorities = (List<String>)claims.get("authorities");
        List<? extends GrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        jwtToken.setAuthorities(grantedAuthorities);
        //String refreshToken = claims.get("orion.refresh.token", String.class);
        //jwtToken.setDoesRefreshTokenExistInDatabase(AuthenticationTokenService.doesRefreshTokenExistByTokenAndUserID(refreshToken, userID));
        String refreshTokenExpirationInEpochMillisecondsString = claims.get("orion.refresh.token.expiration.timestamp.in.epoch.milliseconds", String.class);
        long refreshTokenExpirationInEpochMilliseconds = -1L;
        if(refreshTokenExpirationInEpochMillisecondsString != null)
        {
            refreshTokenExpirationInEpochMilliseconds = Long.parseLong(refreshTokenExpirationInEpochMillisecondsString);
        }
        //jwtToken.setRefreshToken(refreshToken);
        /*jwtToken.setRefreshTokenExpirationDateTime(CalendarService.convertEpochMillisecondsToDateTime(refreshTokenExpirationInEpochMilliseconds));
        jwtToken.setExpiredRefreshToken(CalendarService.hasExpired(jwtToken.getRefreshTokenExpirationDateTime()));
        com.orion.core.calendar.date.Date expirationDate = com.orion.core.calendar.date.Date.of(claims.getExpiration().getYear() + 1900,
                        claims.getExpiration().getMonth() + 1,
                        claims.getExpiration().getDate());
        Time expirationTime = Time.of(claims.getExpiration().getHours(),
                        claims.getExpiration().getMinutes(),
                        claims.getExpiration().getSeconds());
        JWTToken.setTokenExpirationDateTime(DateTime.of(expirationDate, expirationTime));*/
    }
}
