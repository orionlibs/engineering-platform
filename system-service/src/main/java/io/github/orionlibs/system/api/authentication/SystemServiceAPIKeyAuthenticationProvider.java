package io.github.orionlibs.system.api.authentication;

import io.github.orionlibs.core.api.key.ApiKeyAuthenticationToken;
import io.github.orionlibs.core.user.model.UserDetailsWithUserID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SystemServiceAPIKeyAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private SystemServiceAPIKeyValidationService validationService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        ApiKeyAuthenticationToken token = (ApiKeyAuthenticationToken)authentication;
        UserDetailsWithUserID user = validationService.validate(token.getApiKey(), token.getApiSecret());
        if(user == null)
        {
            throw new BadCredentialsException("Invalid API key/secret");
        }
        return new ApiKeyAuthenticationToken(
                        user,
                        user.getAuthorities(),
                        token.getApiKey(),
                        token.getApiSecret()
        );
    }


    @Override
    public boolean supports(Class<?> authentication)
    {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
