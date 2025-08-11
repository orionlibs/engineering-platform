package io.github.orionlibs.user.account;

import io.github.orionlibs.core.user.AccountDetailsDTO;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService
{
    @Autowired
    private UserDAO dao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserModelToDTOConverter userModelToDTOConverter;


    @Transactional
    public boolean enable(String userID)
    {
        Optional<UserModel> userWrap = userService.loadUserByUserID(userID);
        if(userWrap.isPresent())
        {
            UserModel user = userWrap.get();
            user.setEnabled(true);
            userService.saveUser(user);
            return true;
        }
        else
        {
            return false;
        }
    }


    @Transactional
    public boolean disable(String userID)
    {
        Optional<UserModel> userWrap = userService.loadUserByUserID(userID);
        if(userWrap.isPresent())
        {
            UserModel user = userWrap.get();
            user.setEnabled(false);
            userService.saveUser(user);
            return true;
        }
        else
        {
            return false;
        }
    }


    @Transactional(readOnly = true)
    public AccountDetailsDTO getDetailsByUserID(String userID)
    {
        Optional<UserModel> userWrap = dao.findByUserID(UUID.fromString(userID));
        if(userWrap.isPresent())
        {
            return userModelToDTOConverter.convert(userWrap.get());
        }
        return new AccountDetailsDTO("");
    }
}
