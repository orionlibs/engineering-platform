package io.github.orionlibs.alarm;

import io.github.orionlibs.alarm.model.AlarmModel;
import io.github.orionlibs.alarm.model.AlarmsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlarmService
{
    @Autowired private AlarmsDAO dao;


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
