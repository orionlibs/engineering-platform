package io.github.orionlibs.alarm.api.authentication;

import io.github.orionlibs.core.user.authentication.JWTService;
import io.github.orionlibs.core.user.authentication.JWTToken;
import io.github.orionlibs.core.user.model.UserDetailsWithUserID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmServiceAPIKeyValidationService
{
    @Autowired private JWTService jwtService;


    public UserDetailsWithUserID validate(String apiKey, String apiSecret)
    {
        JWTToken tokenData = jwtService.getJWTTokenData(apiKey);
        return new UserDetailsWithUserID(tokenData.getUserID(), tokenData.getAuthorities());
    }
}
