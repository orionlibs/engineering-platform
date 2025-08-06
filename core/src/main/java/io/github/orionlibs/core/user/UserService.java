package io.github.orionlibs.core.user;

import io.github.orionlibs.core.user.model.OrionUserDetails;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserDAO dao;


    @Override
    @Transactional(readOnly = true)
    public OrionUserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserModel user = loadUserAsModelByUsername(username);
        OrionUserDetails userDetails = new OrionUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, user.getAuthorities());
        return userDetails;
    }


    @Transactional(readOnly = true)
    public UserModel loadUserAsModelByUsername(String username) throws UsernameNotFoundException
    {
        return dao.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Transactional(readOnly = true)
    public Optional<UserModel> loadUserByUserID(String userID)
    {
        return loadUserByUserID(UUID.fromString(userID));
    }


    @Transactional(readOnly = true)
    public Optional<UserModel> loadUserByUserID(UUID userID)
    {
        return dao.findByUserID(userID);
    }


    @Transactional
    public UserModel saveUser(UserModel user)
    {
        return dao.save(user);
    }


    @Transactional
    public void deleteAll()
    {
        dao.deleteAll();
    }
}
