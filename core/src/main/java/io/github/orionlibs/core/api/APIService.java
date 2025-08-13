package io.github.orionlibs.core.api;

import io.github.orionlibs.core.user.SessionService;
import jakarta.servlet.http.HttpServletRequest;

public class APIService
{
    protected String getUserID(HttpServletRequest request)
    {
        return SessionService.getUserID(request);
    }
}
