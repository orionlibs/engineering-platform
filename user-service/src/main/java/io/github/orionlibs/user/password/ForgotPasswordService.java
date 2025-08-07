package io.github.orionlibs.user.password;

import io.github.orionlibs.core.email.EmailService;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.OrionUserDetails;
import io.github.orionlibs.user.model.ForgotPasswordRequestModel;
import io.github.orionlibs.user.model.ForgotPasswordRequestsDAO;
import io.github.orionlibs.user.password.api.CreateForgotPasswordRequestRequest;
import java.time.LocalDateTime;
import java.util.UUID;
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
    @Autowired
    private EmailService emailer;


    //@Transactional(readOnly = true)
    public boolean process(CreateForgotPasswordRequestRequest request)
    {
        try
        {
            OrionUserDetails user = userService.loadUserByUsername(request.getUsername());
            String forgotPasswordCode = UUID.randomUUID().toString();
            ForgotPasswordRequestModel model = new ForgotPasswordRequestModel();
            model.setUserID(user.getUserID());
            model.setForgotPasswordCode(forgotPasswordCode);
            model.setExpiresAt(LocalDateTime.now().plusMinutes(20L));
            model = dao.saveAndFlush(model);
            emailer.sendEmail();
            return true;
        }
        catch(UsernameNotFoundException e)
        {
            return false;
        }
    }


    public boolean processCode(String forgotPasswordCode)
    {
        return true;
    }


    public boolean isCodeValid(String forgotPasswordCode)
    {
        dao.findByForgotPasswordCode(forgotPasswordCode);
        return true;
    }
}
