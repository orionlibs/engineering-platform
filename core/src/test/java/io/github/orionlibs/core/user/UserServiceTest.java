package io.github.orionlibs.core.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest
{
    @Autowired UserDAO dao;
    @Autowired UserService userService;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void loadUserByUsername()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        UserModel newUser = dao.save(userTemp);
        assertThat(newUser.getCreatedAt()).isNotNull();
        assertThat(newUser.getUpdatedAt()).isNotNull();
        UserDetails user = userService.loadUserByUsername("me@email.com");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("me@email.com");
        assertThat(user.getPassword().isEmpty()).isFalse();
        assertThat(user.getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority("USER")));
    }


    @Test
    void loadUserByUsername_notFound()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        UserModel newUser = dao.save(userTemp);
        assertThatThrownBy(() -> userService.loadUserByUsername("nonexistentemail@email.com")).isInstanceOf(UsernameNotFoundException.class)
                        .hasMessage("User not found");
    }


    @Test
    void loadUserByUserID()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        UserModel newUser = dao.save(userTemp);
        assertThat(newUser.getCreatedAt()).isNotNull();
        assertThat(newUser.getUpdatedAt()).isNotNull();
        Optional<UserModel> user = userService.loadUserByUserID(newUser.getId());
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUsername()).isEqualTo("me@email.com");
        assertThat(user.get().getPassword().isEmpty()).isFalse();
        assertThat(user.get().getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority("USER")));
    }


    @Test
    void loadUserByUserID_notFound()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        UserModel newUser = dao.save(userTemp);
        Optional<UserModel> userWrap = userService.loadUserByUserID(UUID.randomUUID().toString());
        assertThat(userWrap.isEmpty()).isTrue();
    }


    @Test
    void loadUserByUserID_UUIDAsString()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        UserModel newUser = dao.save(userTemp);
        assertThat(newUser.getCreatedAt()).isNotNull();
        assertThat(newUser.getUpdatedAt()).isNotNull();
        Optional<UserModel> user = userService.loadUserByUserID(newUser.getId().toString());
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getUsername()).isEqualTo("me@email.com");
        assertThat(user.get().getPassword().isEmpty()).isFalse();
        assertThat(user.get().getAuthorities()).isEqualTo(Set.of(new SimpleGrantedAuthority("USER")));
    }


    @Test
    void loadUserByUserID_UUIDAsString_notFound()
    {
        UserModel userTemp = new UserModel(hmacSHAEncryptionKeyProvider);
        userTemp.setUsername("me@email.com");
        userTemp.setPassword("4528");
        userTemp.setAuthority("USER");
        userTemp.setFirstName("Dimi");
        userTemp.setLastName("Emilson");
        userTemp.setPhoneNumber("07896620211");
        UserModel newUser = dao.save(userTemp);
        Optional<UserModel> userWrap = userService.loadUserByUserID(UUID.randomUUID().toString());
        assertThat(userWrap.isEmpty()).isTrue();
    }
}
