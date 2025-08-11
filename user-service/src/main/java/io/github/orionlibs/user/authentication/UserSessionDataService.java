package io.github.orionlibs.user.authentication;

import io.github.orionlibs.core.user.SessionAttribute;
import io.github.orionlibs.core.user.SessionService;
import io.github.orionlibs.core.user.authentication.JWTToken;
import io.github.orionlibs.core.user.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
public class UserSessionDataService
{
    public void saveUserInSession(HttpServletRequest request, UserModel user, JWTToken tokenData)
    {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
        SessionService.setAttribute(request, SessionAttribute.currentUserJWTToken, tokenData.getToken());
        SessionService.setAttribute(request, SessionAttribute.currentUserJWTTokenData, tokenData);
        SessionService.setAttribute(request, SessionAttribute.currentUserID, tokenData.getUserID());
        SessionService.setAttribute(request, SessionAttribute.currentUsername, user.getUsername());
        SessionService.setAttribute(request, SessionAttribute.currentUserAuthority, user.getAuthority());
    }
}
