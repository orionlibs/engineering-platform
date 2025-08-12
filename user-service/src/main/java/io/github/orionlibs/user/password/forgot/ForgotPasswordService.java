package io.github.orionlibs.user.password.forgot;

import io.github.orionlibs.core.Logger;
import io.github.orionlibs.core.email.EmailService;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.OrionUserDetails;
import io.github.orionlibs.user.password.forgot.api.CreateForgotPasswordRequestRequest;
import io.github.orionlibs.user.password.forgot.model.ForgotPasswordRequestModel;
import io.github.orionlibs.user.password.forgot.model.ForgotPasswordRequestsDAO;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService
{
    @Autowired private UserService userService;
    @Autowired private ForgotPasswordRequestsDAO dao;
    @Autowired private EmailService emailer;
    @Autowired private ForgotPasswordCodeGenerator forgotPasswordCodeGenerator;
    @Autowired private ForgotPasswordCodeValidator forgotPasswordCodeValidator;


    public boolean process(CreateForgotPasswordRequestRequest request)
    {
        try
        {
            OrionUserDetails user = userService.loadUserByUsername(request.getUsername());
            String forgotPasswordCode = forgotPasswordCodeGenerator.generateString();
            request.setForgotPasswordCodeGenerated(forgotPasswordCode);
            ForgotPasswordRequestModel model = new ForgotPasswordRequestModel();
            model.setUserID(user.getUserID());
            model.setForgotPasswordCode(forgotPasswordCode);
            model.setExpiresAt(LocalDateTime.now().plusMinutes(20L));
            model = dao.saveAndFlush(model);
            emailer.sendEmail();
            Logger.info("Processed forgot password request");
            return true;
        }
        catch(UsernameNotFoundException e)
        {
            return false;
        }
    }


    public void deleteRequest(String forgotPasswordCode)
    {
        dao.deleteByForgotPasswordCode(forgotPasswordCode);
    }


    public boolean isCodeValid(String forgotPasswordCode)
    {
        Optional<ForgotPasswordRequestModel> model = dao.findByForgotPasswordCode(forgotPasswordCode);
        return model.map(m -> forgotPasswordCodeValidator.isValid(m))
                        .orElse(false);
    }


    public String getUserIDByForgotPasswordCode(String forgotPasswordCode)
    {
        return dao.findUsesrIDByForgotPasswordCode(forgotPasswordCode);
    }
}
