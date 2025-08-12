package io.github.orionlibs.user.account;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.UserService;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest
{
    @Autowired UserDAO dao;
    @Autowired UserService userService;
    @Autowired AccountService accountService;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void enableAccount()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        userTemp.setEnabled(false);
        UserModel newUser = dao.save(userTemp);
        UserDetails user = userService.loadUserByUsername("me@email.com");
        assertThat(user.isEnabled()).isFalse();
        userTemp.setEnabled(true);
        newUser = dao.save(userTemp);
        user = userService.loadUserByUsername("me@email.com");
        assertThat(user.isEnabled()).isTrue();
    }


    @Test
    void disableAccount()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        userTemp.setEnabled(false);
        UserModel newUser = dao.save(userTemp);
        UserDetails user = userService.loadUserByUsername("me@email.com");
        assertThat(user.isEnabled()).isFalse();
    }


    @Test
    void delete()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        userTemp.setEnabled(false);
        UserModel newUser = dao.save(userTemp);
        Optional<UserModel> userWrap = userService.loadUserByUserID(newUser.getId().toString());
        assertThat(userWrap.isPresent()).isTrue();
        accountService.delete(newUser.getId());
        userWrap = userService.loadUserByUserID(newUser.getId().toString());
        assertThat(userWrap.isEmpty()).isTrue();
    }
}
