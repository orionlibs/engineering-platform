package io.github.orionlibs.core.user.converter;

import io.github.orionlibs.core.converter.Converter;
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
