package io.github.orionlibs.core.api.key;

import io.github.orionlibs.core.api.key.model.ApiKeyDAO;
import io.github.orionlibs.core.api.key.model.ApiKeyModel;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserDetailsWithUserID;
import io.github.orionlibs.core.user.model.UserModel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyValidationService
{
    @Autowired
    private ApiKeyDAO apiKeyDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserDetailsService userService;


    public UserDetailsWithUserID validate(String apiKey, String apiSecret) throws AuthenticationException
    {
        Optional<ApiKeyModel> opt = apiKeyDAO.findById(apiKey);
        if(opt.isEmpty())
        {
            return null;
        }
        ApiKeyModel model = opt.get();
        if(!model.getApiSecret().equals(apiSecret))
        {
            return null;
        }
        Optional<UserModel> userFound = userDAO.findByUserID(model.getUserID());
        if(userFound.isPresent())
        {
            UserDetails user = userService.loadUserByUsername(userFound.get().getUsername());
            return new UserDetailsWithUserID(model.getUserID().toString(), user);
        }
        else
        {
            throw new BadCredentialsException("User not found");
        }
        /*return userDAO.findByUserID(model.getUserID())
                        .map(user -> userService.loadUserByUsername(user.getUsername()))
                        .orElseThrow(() -> new BadCredentialsException("User not found"));*/
    }
}
