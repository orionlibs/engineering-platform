package io.github.orionlibs.user;

import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.user.api.CreateForgotPasswordRequestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService
{
    @Autowired
    private UserService userService;


    //@Transactional(readOnly = true)
    public boolean process(CreateForgotPasswordRequestRequest request)
    {
        try
        {
            UserDetails user = userService.loadUserByUsername(request.getUsername());
            return true;
        }
        catch(UsernameNotFoundException e)
        {
            return false;
        }
    }
}
