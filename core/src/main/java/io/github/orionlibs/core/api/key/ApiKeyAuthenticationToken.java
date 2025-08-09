package io.github.orionlibs.core.api.key;

import io.github.orionlibs.core.user.model.UserDetailsWithUserID;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken
{
    private final String apiKey;
    private final String apiSecret;
    private String userID;


    public ApiKeyAuthenticationToken(String apiKey, String apiSecret)
    {
        super(null);
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        setAuthenticated(false);
    }


    public ApiKeyAuthenticationToken(UserDetailsWithUserID principal, Collection<? extends GrantedAuthority> auths, String apiKey, String apiSecret)
    {
        super(auths);
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.userID = principal.getUserID().toString();
        setAuthenticated(true);
        setDetails(principal);
    }


    public String getApiKey()
    {
        return apiKey;
    }


    public String getApiSecret()
    {
        return apiSecret;
    }


    @Override
    public Object getCredentials()
    {
        return apiSecret;
    }


    @Override
    public Object getPrincipal()
    {
        return getDetails();
    }


    public String getUserID()
    {
        return userID;
    }
}
