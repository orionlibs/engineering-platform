package io.github.orionlibs.alarm.model;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmsDAO extends JpaRepository<AlarmModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    List<AlarmModel> findByTagIDAndIsEnabledTrue(String tagID);


    List<AlarmModel> findByIsEnabledTrue();


    List<AlarmModel> findByIsEnabledFalse();


    @Query("SELECT COUNT(1) FROM AlarmModel c WHERE c.isEnabled = true")
    long findNumberOfEnabledAlarms();


    @Query("SELECT COUNT(1) FROM AlarmModel c WHERE c.isEnabled = false")
    long findNumberOfDisabledAlarms();
}
