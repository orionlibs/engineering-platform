package io.github.orionlibs.system.configuration;

import io.github.orionlibs.system.configuration.model.ConfigurationDAO;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import io.github.orionlibs.system.converter.ConfigurationDTOToModelConverter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigurationService
{
    @Autowired
    private ConfigurationDAO dao;
    @Autowired
    private ConfigurationDTOToModelConverter configurationDTOToModelConverter;


    @Transactional(readOnly = true)
    public List<ConfigurationModel> getAll()
    {
        return dao.findAll();
    }


    @Transactional(readOnly = true)
    public Optional<ConfigurationModel> findByKey(String key)
    {
        return dao.findByKey(key);
    }


    @Transactional(readOnly = true)
    public boolean existsByKey(String key)
    {
        return dao.existsByKey(key);
    }


    @Transactional
    public ConfigurationModel update(String key, String value)
    {
        return dao.saveAndFlush(new ConfigurationModel(key, value));
    }
}
