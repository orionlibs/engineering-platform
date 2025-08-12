package io.github.orionlibs.core.api.header;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeaderService
{
    @Autowired private AuthenticationTokenExtractor authenticationTokenExtractor;


    public String extractAuthorisationBearerToken(HttpServletRequest request)
    {
        return authenticationTokenExtractor.extract(request);
    }
}
