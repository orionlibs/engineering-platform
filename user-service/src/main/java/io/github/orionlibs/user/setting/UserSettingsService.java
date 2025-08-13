package io.github.orionlibs.user.setting;

import io.github.orionlibs.core.Logger;
import io.github.orionlibs.core.user.model.UserModel;
import io.github.orionlibs.core.user.setting.model.UserSettingsDAO;
import io.github.orionlibs.core.user.setting.model.UserSettingsModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSettingsService
{
    @Autowired private UserSettingsDAO dao;
    @Autowired private DefaultUserSettings defaultUserSettings;


    @Transactional(readOnly = true)
    public Optional<UserSettingsModel> getByID(UUID settingID)
    {
        return dao.findById(settingID);
    }


    @Transactional(readOnly = true)
    public Optional<UserSettingsModel> getByID(String settingID)
    {
        return getByID(UUID.fromString(settingID));
    }


    @Transactional(readOnly = true)
    public Optional<UserSettingsModel> getByIDAndUserID(String settingID, String userID)
    {
        return dao.findByIdAndUser_Id(UUID.fromString(settingID), UUID.fromString(userID));
    }


    @Transactional(readOnly = true)
    public Optional<UserSettingsModel> getBySettingNameAndUserID(String settingName, String userID)
    {
        return dao.findBySettingNameAndUser_Id(settingName, UUID.fromString(userID));
    }


    @Transactional(readOnly = true)
    public List<UserSettingsModel> getByUserID(UUID userID)
    {
        return dao.findAllByUserId(userID);
    }


    @Transactional(readOnly = true)
    public List<UserSettingsModel> getByUserID(String userID)
    {
        return getByUserID(UUID.fromString(userID));
    }


    @Transactional
    public UserSettingsModel save(UserSettingsModel model)
    {
        UserSettingsModel saved = dao.saveAndFlush(model);
        Logger.info("User setting saved");
        return saved;
    }


    @Transactional
    public boolean update(UserSettingsModel model)
    {
        UserSettingsModel updated = save(model);
        Logger.info("Updated user setting");
        return true;
    }


    @Transactional
    public boolean updateByID(String settingID, String settingValue)
    {
        Optional<UserSettingsModel> modelWrap = getByID(settingID);
        if(modelWrap.isPresent())
        {
            UserSettingsModel model = modelWrap.get();
            model.setSettingValue(settingValue);
            UserSettingsModel updated = save(model);
        }
        Logger.info("Updated user setting");
        return true;
    }


    @Transactional
    public boolean updateByIDAndUserID(String settingID, String settingValue, String userID)
    {
        Optional<UserSettingsModel> modelWrap = getByIDAndUserID(settingID, userID);
        if(modelWrap.isPresent())
        {
            UserSettingsModel model = modelWrap.get();
            model.setSettingValue(settingValue);
            UserSettingsModel updated = save(model);
        }
        Logger.info("Updated user setting");
        return true;
    }


    @Transactional
    public boolean updateByName(String settingName, String settingValue, String userID)
    {
        Optional<UserSettingsModel> modelWrap = getBySettingNameAndUserID(settingName, userID);
        if(modelWrap.isPresent())
        {
            UserSettingsModel model = modelWrap.get();
            model.setSettingValue(settingValue);
            UserSettingsModel updated = save(model);
        }
        Logger.info("Updated user setting");
        return true;
    }


    @Transactional
    public void saveDefaultSettingsForUser(UserModel user)
    {
        for(DefaultUserSettings.Setting def : defaultUserSettings.getSettings())
        {
            UserSettingsModel s = new UserSettingsModel(user, def.getName(), def.getValue());
            save(s);
        }
        Logger.info("Saved default user settings for user");
    }
}
