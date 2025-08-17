package io.github.orionlibs.alarm;

import io.github.orionlibs.alarm.model.AlarmModel;
import io.github.orionlibs.alarm.model.AlarmsDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmService
{
    @Autowired private AlarmsDAO dao;


    @Transactional(readOnly = true)
    public long getNumberOfAlarms()
    {
        return dao.count();
    }


    @Transactional(readOnly = true)
    public long getNumberOfEnabledAlarms()
    {
        return dao.findNumberOfEnabledAlarms();
    }


    @Transactional(readOnly = true)
    public long getNumberOfDisabledAlarms()
    {
        return dao.findNumberOfDisabledAlarms();
    }


    @Transactional(readOnly = true)
    public Optional<AlarmModel> getByID(String alarmID)
    {
        return dao.findById(UUID.fromString(alarmID));
    }


    @Transactional(readOnly = true)
    public List<AlarmModel> getEnabledAlarms()
    {
        return dao.findByIsEnabledTrue();
    }


    @Transactional(readOnly = true)
    public List<AlarmModel> getDisabledAlarms()
    {
        return dao.findByIsEnabledFalse();
    }


    @Transactional(readOnly = true)
    public List<AlarmModel> getEnabledAlarmsByTagID(String tagID)
    {
        return dao.findByTagIDAndIsEnabledTrue(tagID);
    }


    @Transactional
    public AlarmModel save(AlarmModel model)
    {
        return dao.saveAndFlush(model);
    }


    @Transactional
    public AlarmModel update(AlarmModel model)
    {
        return dao.saveAndFlush(model);
    }


    @Transactional
    public void deleteAll()
    {
        dao.deleteAll();
    }
}
