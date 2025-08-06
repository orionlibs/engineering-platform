package io.github.orionlibs.user;

import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.OrionUserDetails;
import io.github.orionlibs.user.api.CreateForgotPasswordRequestRequest;
import io.github.orionlibs.user.model.ForgotPasswordRequestModel;
import io.github.orionlibs.user.model.ForgotPasswordRequestsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService
{
    @Autowired
    private UserService userService;
    @Autowired
    private ForgotPasswordRequestsDAO dao;


    //@Transactional(readOnly = true)
    public boolean process(CreateForgotPasswordRequestRequest request)
    {
        try
        {
            OrionUserDetails user = userService.loadUserByUsername(request.getUsername());
            ForgotPasswordRequestModel model = new ForgotPasswordRequestModel();
            model.setUserID(user.getUserID());
            dao.saveAndFlush(model);
            return true;
        }
        catch(UsernameNotFoundException e)
        {
            return false;
        }
    }
}
