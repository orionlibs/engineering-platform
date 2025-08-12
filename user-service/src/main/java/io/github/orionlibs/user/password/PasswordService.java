package io.github.orionlibs.user.password;

import io.github.orionlibs.core.Logger;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.user.password.api.AdminUpdatePasswordRequest;
import io.github.orionlibs.user.password.api.UpdatePasswordRequest;
import io.github.orionlibs.user.password.forgot.ForgotPasswordService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordService
{
    @Autowired private UserService userService;
    @Autowired private ForgotPasswordService forgotPasswordService;


    @Transactional
    public boolean update(String userID, UpdatePasswordRequest request)
    {
        Optional<UserModel> userWrap = userService.loadUserByUserID(userID);
        if(userWrap.isEmpty())
        {
            return false;
        }
        UserModel user = userWrap.get();
        user.setPassword(request.getPassword());
        userService.saveUser(user);
        Logger.info("Updated user password");
        return true;
    }


    @Transactional
    public boolean update(AdminUpdatePasswordRequest request)
    {
        Optional<UserModel> userWrap = userService.loadUserByUserID(request.getUserID());
        if(userWrap.isEmpty())
        {
            return false;
        }
        UserModel user = userWrap.get();
        user.setPassword(request.getPassword());
        userService.saveUser(user);
        Logger.info("Updated user password");
        return true;
    }


    @Transactional
    public boolean update(UpdatePasswordRequest request, String forgotPasswordCode)
    {
        String userID = forgotPasswordService.getUserIDByForgotPasswordCode(forgotPasswordCode);
        boolean updated = update(userID, request);
        if(updated)
        {
            forgotPasswordService.deleteRequest(forgotPasswordCode);
        }
        return updated;
    }
}
