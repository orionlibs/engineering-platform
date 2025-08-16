package io.github.orionlibs.alarm;

import io.github.orionlibs.alarm.model.AlarmEventModel;
import io.github.orionlibs.alarm.model.AlarmEventsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmEventService
{
    @Autowired private AlarmEventsDAO dao;


    @Transactional(readOnly = true)
    public Optional<AlarmEventModel> getByID(String alarmEventID)
    {
        return dao.findById(UUID.fromString(alarmEventID));
    }


    @Transactional
    public AlarmEventModel save(AlarmEventModel model)
    {
        return dao.saveAndFlush(model);
    }


    @Transactional
    public AlarmEventModel update(AlarmEventModel model)
    {
        return dao.saveAndFlush(model);
    }


    @Transactional
    public void deleteAll()
    {
        dao.deleteAll();
    }
}
