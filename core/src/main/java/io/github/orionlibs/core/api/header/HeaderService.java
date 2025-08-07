package io.github.orionlibs.core.api.header;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class HeaderService
{
    public String extractAuthorisationBearerToken(HttpServletRequest request)
    {
        String token = null;
        String authHeader = request.getHeader(HTTPHeader.Authorization.get());
        if(authHeader != null && authHeader.startsWith(HTTPHeaderValue.Bearer.get()))
        {
            token = authHeader.substring(7);
        }
        return token;
    }
}
