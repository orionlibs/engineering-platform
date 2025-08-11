package io.github.orionlibs.core.api.header;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class HeaderService
{
    public String extractAuthorisationBearerToken(HttpServletRequest request)
    {
        String accessKey = request.getHeader(HTTPHeader.Authorization.get());
        if(accessKey == null)
        {
            accessKey = request.getHeader(HTTPHeader.XAPIKey.get());
            if(accessKey != null && accessKey.startsWith(HTTPHeaderValue.Bearer.get()))
            {
                accessKey = accessKey.substring(7);
            }
        }
        return accessKey;
    }
}
