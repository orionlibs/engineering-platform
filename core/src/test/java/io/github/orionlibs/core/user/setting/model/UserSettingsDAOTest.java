package io.github.orionlibs.core.user.setting.model;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserSettingsDAOTest
{
    @Autowired UserDAO userDAO;
    @Autowired UserSettingsDAO dao;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;
    UserModel user;
    UserSettingsModel setting1;
    UserSettingsModel setting2;


    @BeforeEach
    void setup()
    {
        userDAO.deleteAll();
        dao.deleteAll();
    }


    @Test
    void findById()
    {
        saveUserSettings();
        Optional<UserSettingsModel> setting1Wrap = dao.findById(setting1.getId());
        assertThat(setting1Wrap.get().getSettingName()).isEqualTo("setting1");
        assertThat(setting1Wrap.get().getSettingValue()).isEqualTo("50");
        Optional<UserSettingsModel> setting2Wrap = dao.findById(setting2.getId());
        assertThat(setting2Wrap.get().getSettingName()).isEqualTo("setting2");
        assertThat(setting2Wrap.get().getSettingValue()).isEqualTo("100");
    }


    @Test
    void findAllByUserId()
    {
        saveUserSettings();
        List<UserSettingsModel> settings = dao.findAllByUserId(user.getId());
        assertThat(settings.size()).isEqualTo(2);
        assertThat(settings.get(0).getSettingName()).isEqualTo("setting1");
        assertThat(settings.get(0).getSettingValue()).isEqualTo("50");
        assertThat(settings.get(1).getSettingName()).isEqualTo("setting2");
        assertThat(settings.get(1).getSettingValue()).isEqualTo("100");
    }


    @Test
    void findByIdAndUser_Id()
    {
        saveUserSettings();
        Optional<UserSettingsModel> setting1Wrap = dao.findByIdAndUser_Id(setting1.getId(), user.getId());
        assertThat(setting1Wrap.get().getSettingName()).isEqualTo("setting1");
        assertThat(setting1Wrap.get().getSettingValue()).isEqualTo("50");
        Optional<UserSettingsModel> setting2Wrap = dao.findByIdAndUser_Id(setting2.getId(), user.getId());
        assertThat(setting2Wrap.get().getSettingName()).isEqualTo("setting2");
        assertThat(setting2Wrap.get().getSettingValue()).isEqualTo("100");
    }


    @Test
    void findBySettingNameAndUser_Id()
    {
        saveUserSettings();
        Optional<UserSettingsModel> setting1Wrap = dao.findBySettingNameAndUser_Id("setting1", user.getId());
        assertThat(setting1Wrap.get().getSettingName()).isEqualTo("setting1");
        assertThat(setting1Wrap.get().getSettingValue()).isEqualTo("50");
        Optional<UserSettingsModel> setting2Wrap = dao.findBySettingNameAndUser_Id("setting2", user.getId());
        assertThat(setting2Wrap.get().getSettingName()).isEqualTo("setting2");
        assertThat(setting2Wrap.get().getSettingValue()).isEqualTo("100");
    }


    private void saveUserSettings()
    {
        user = new UserModel(hmacSHAEncryptionKeyProvider);
        user.setUsername("me@email.com");
        user.setPassword("4528");
        user.setAuthority("USER");
        user.setFirstName("Dimi");
        user.setLastName("Emilson");
        user.setPhoneNumber("07896620211");
        user = userDAO.save(user);
        setting1 = new UserSettingsModel(user, "setting1", "50");
        setting1 = dao.save(setting1);
        setting2 = new UserSettingsModel(user, "setting2", "100");
        setting2 = dao.save(setting2);
    }
}
