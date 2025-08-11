package io.github.orionlibs.user.account;

import io.github.orionlibs.core.Converter;
import io.github.orionlibs.core.user.AccountDetailsDTO;
import io.github.orionlibs.core.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserModelToDTOConverter implements Converter<UserModel, AccountDetailsDTO>
{
    @Override
    public AccountDetailsDTO convert(UserModel objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new AccountDetailsDTO(objectToConvert.getUsername());
    }
}
