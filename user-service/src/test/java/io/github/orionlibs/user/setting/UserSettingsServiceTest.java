package io.github.orionlibs.user.setting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.orionlibs.core.cryptology.HMACSHAEncryptionKeyProvider;
import io.github.orionlibs.core.user.model.UserDAO;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.core.user.setting.model.UserSettingsDAO;
import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserSettingsServiceTest
{
    @Autowired UserDAO userDAO;
    @Autowired UserSettingsDAO dao;
    @Autowired UserSettingsService userSettingsService;
    @Autowired HMACSHAEncryptionKeyProvider hmacSHAEncryptionKeyProvider;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
        userDAO.deleteAll();
    }


    @Test
    void getByID()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        assertThat(setting).isNotNull();
        assertThat(setting.getSettingName()).isEqualTo("setting1");
        assertThat(setting.getSettingValue()).isEqualTo("yes");
        assertThat(setting.getUser().getUsername()).isEqualTo("me@email.com");
        UserSettingsModel setting1 = userSettingsService.getByID(setting.getId()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting1");
        assertThat(setting1.getSettingValue()).isEqualTo("yes");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
        setting1 = userSettingsService.getByID(setting.getId().toString()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting1");
        assertThat(setting1.getSettingValue()).isEqualTo("yes");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void getByIDAndUserID()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        UserSettingsModel setting1 = userSettingsService.getByIDAndUserID(setting.getId().toString(), userModel.getId().toString()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting1");
        assertThat(setting1.getSettingValue()).isEqualTo("yes");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void getBySettingNameAndUserID()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        UserSettingsModel setting1 = userSettingsService.getBySettingNameAndUserID("setting1", userModel.getId().toString()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting1");
        assertThat(setting1.getSettingValue()).isEqualTo("yes");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void getByUserID()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting1 = new UserSettingsModel(newUser, "setting1", "yes");
        setting1 = userSettingsService.save(setting1);
        UserSettingsModel setting2 = new UserSettingsModel(newUser, "setting2", "no");
        setting2 = userSettingsService.save(setting2);
        List<UserSettingsModel> models = userSettingsService.getByUserID(userModel.getId());
        assertThat(models.get(0).getSettingName()).isEqualTo("setting1");
        assertThat(models.get(0).getSettingValue()).isEqualTo("yes");
        assertThat(models.get(0).getUser().getUsername()).isEqualTo("me@email.com");
        assertThat(models.get(1).getSettingName()).isEqualTo("setting2");
        assertThat(models.get(1).getSettingValue()).isEqualTo("no");
        assertThat(models.get(1).getUser().getUsername()).isEqualTo("me@email.com");
        models = userSettingsService.getByUserID(userModel.getId().toString());
        assertThat(models.get(0).getSettingName()).isEqualTo("setting1");
        assertThat(models.get(0).getSettingValue()).isEqualTo("yes");
        assertThat(models.get(0).getUser().getUsername()).isEqualTo("me@email.com");
        assertThat(models.get(1).getSettingName()).isEqualTo("setting2");
        assertThat(models.get(1).getSettingValue()).isEqualTo("no");
        assertThat(models.get(1).getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void update()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        setting.setSettingName("setting2");
        setting.setSettingValue("no");
        userSettingsService.update(setting);
        UserSettingsModel setting1 = userSettingsService.getByID(setting.getId().toString()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting2");
        assertThat(setting1.getSettingValue()).isEqualTo("no");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void updateByID()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        setting.setSettingValue("no");
        userSettingsService.updateByID(setting.getId().toString(), "no");
        UserSettingsModel setting1 = userSettingsService.getByID(setting.getId().toString()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting1");
        assertThat(setting1.getSettingValue()).isEqualTo("no");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void updateByIDAndUserID()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        setting.setSettingValue("no");
        userSettingsService.updateByIDAndUserID(setting.getId().toString(), "no", userModel.getId().toString());
        UserSettingsModel setting1 = userSettingsService.getByID(setting.getId().toString()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting1");
        assertThat(setting1.getSettingValue()).isEqualTo("no");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void updateByName()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        UserSettingsModel setting = new UserSettingsModel(newUser, "setting1", "yes");
        setting = userSettingsService.save(setting);
        setting.setSettingValue("no");
        userSettingsService.updateByName("setting1", "no", userModel.getId().toString());
        UserSettingsModel setting1 = userSettingsService.getByID(setting.getId().toString()).get();
        assertThat(setting1).isNotNull();
        assertThat(setting1.getSettingName()).isEqualTo("setting1");
        assertThat(setting1.getSettingValue()).isEqualTo("no");
        assertThat(setting1.getUser().getUsername()).isEqualTo("me@email.com");
    }


    @Test
    void saveUserSetting_noUserInDatabase()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserSettingsModel setting = new UserSettingsModel(userModel, "setting1", "yes");
        assertThatThrownBy(() -> userSettingsService.save(setting)).isInstanceOf(InvalidDataAccessApiUsageException.class)
                        .hasMessageContaining("TransientPropertyValueException: Not-null property references a transient value");
    }


    @Test
    void saveDefaultUserSettings()
    {
        UserModel userModel = new UserModel(hmacSHAEncryptionKeyProvider);
        userModel.setUsername("me@email.com");
        userModel.setPassword("4528");
        userModel.setAuthority("USER");
        userModel.setFirstName("Dimi");
        userModel.setLastName("Emilson");
        userModel.setPhoneNumber("07896620211");
        UserModel newUser = userDAO.save(userModel);
        userSettingsService.saveDefaultSettingsForUser(newUser);
        List<UserSettingsModel> settings = userSettingsService.getByUserID(newUser.getId());
        assertThat(settings).isNotNull();
        assertThat(settings.size()).isEqualTo(1);
        assertThat(settings.get(0).getSettingName()).isEqualTo("theme");
        assertThat(settings.get(0).getSettingValue()).isEqualTo("dark");
    }
}
