package io.github.orionlibs.core.user.setting.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsDAO extends JpaRepository<UserSettingsModel, UUID>
{
    List<UserSettingsModel> findAllByUserId(UUID userID);


    Optional<UserSettingsModel> findByIdAndUser_Id(UUID id, UUID userId);


    Optional<UserSettingsModel> findBySettingNameAndUser_Id(String settingName, UUID userId);
}
