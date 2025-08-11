package io.github.orionlibs.core.api.key;

import io.github.orionlibs.core.api.header.HeaderService;
import io.github.orionlibs.core.user.SessionAttribute;
import io.github.orionlibs.core.user.SessionService;
import io.github.orionlibs.core.user.model.UserDetailsWithUserID;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter
{
    private final AuthenticationManager authManager;
    private final HeaderService headerService;


    public ApiKeyAuthFilter(AuthenticationManager authManager, HeaderService headerService)
    {
        this.authManager = authManager;
        this.headerService = headerService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        String accessKey = headerService.extractAuthorisationBearerToken(request);
        if(accessKey != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            ApiKeyAuthenticationToken token = new ApiKeyAuthenticationToken(accessKey, "");
            try
            {
                Authentication authResult = authManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authResult);
                ApiKeyAuthenticationToken tokenData = (ApiKeyAuthenticationToken)authResult;
                UserDetailsWithUserID userDetails = (UserDetailsWithUserID)tokenData.getPrincipal();
                SessionService.setAttribute(request, SessionAttribute.currentUserJWTToken, accessKey);
                SessionService.setAttribute(request, SessionAttribute.currentUserJWTTokenData, tokenData);
                SessionService.setAttribute(request, SessionAttribute.currentUserID, tokenData.getUserID());
                SessionService.setAttribute(request, SessionAttribute.currentUsername, userDetails.getUsername());
                SessionService.setAttribute(request, SessionAttribute.currentUserAuthority, userDetails.getAuthorities());
            }
            catch(AuthenticationException ex)
            {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
