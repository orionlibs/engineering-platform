package io.github.orionlibs.user.password.forgot;

import io.github.orionlibs.core.Validator;
import io.github.orionlibs.user.password.forgot.model.ForgotPasswordRequestModel;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ForgotPasswordCodeValidator implements Validator
{
    public boolean isValid(ForgotPasswordRequestModel model)
    {
        LocalDateTime now = LocalDateTime.now();
        return model.getExpiresAt().isAfter(now) || model.getExpiresAt().isEqual(now);
    }
}
