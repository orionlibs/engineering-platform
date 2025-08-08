package io.github.orionlibs.user.authentication;

import io.github.orionlibs.core.api.JWTService;
import io.github.orionlibs.core.event.Publishable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements Publishable
{
    @Autowired
    private JWTService jwtService;


    public String logoutUser(HttpServletRequest request, HttpServletResponse response, String token)
    {
        if(token == null || token.isBlank())
        {
            return token;
        }
        SecurityContextHolder.clearContext();
        response.addHeader("Clear-Site-Data", "*");
        if(request != null && request.getSession(false) != null)
        {
            request.getSession(false).invalidate();
        }
        //response.addHeader(HttpHeaders.SET_COOKIE, CookieService.createCookie(CookieName.JWTAccessToken.get(), null, 0).toString());
        return "";
    }
}
