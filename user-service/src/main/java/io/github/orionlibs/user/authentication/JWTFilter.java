package io.github.orionlibs.user.authentication;

import io.github.orionlibs.core.api.header.HeaderService;
import io.github.orionlibs.core.user.AuthenticationService;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.authentication.JWTService;
import io.github.orionlibs.core.user.authentication.JWTToken;
import io.github.orionlibs.core.user.model.UserModel;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter
{
    private final JWTService jwtService;
    private final HeaderService headerService;
    private final UserService userService;
    private final AuthenticationService authenticationService;


    public JWTFilter(JWTService jwtService, HeaderService headerService, UserService userService, AuthenticationService authenticationService)
    {
        this.jwtService = jwtService;
        this.headerService = headerService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException
    {
        String token = headerService.extractAuthorisationBearerToken(req);
        //String userID = jwtService.extractUserID(token);
        JWTToken tokenData = jwtService.getJWTTokenData(token);
        if(tokenData.getUserID() != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            Optional<UserModel> userWrap = userService.loadUserByUserID(tokenData.getUserID());
            if(userWrap.isPresent())
            {
                UserModel user = userWrap.get();
                if(jwtService.isTokenValid(token, user))
                {
                    authenticationService.saveUserInSession(req, user, tokenData);
                }
            }
        }
        chain.doFilter(req, res);
    }
}
