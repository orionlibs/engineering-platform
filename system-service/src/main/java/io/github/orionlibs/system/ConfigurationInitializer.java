package io.github.orionlibs.system;

import io.github.orionlibs.system.configuration.model.ConfigurationDAO;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ConfigurationInitializer implements ApplicationRunner
{
    @Autowired private ConfigurationDAO dao;
    @Autowired private DefaultSystemConfiguration defaultSystemConfiguration;


    @Override
    @Transactional
    public void run(ApplicationArguments args)
    {
        defaultSystemConfiguration.getDefaultConfig().forEach((key, value) -> {
            if(!dao.existsByKey(key))
            {
                dao.save(new ConfigurationModel(key, value));
            }
        });
    }
}
