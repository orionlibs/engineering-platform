package io.github.orionlibs.system;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.system.configuration.model.ConfigurationDAO;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ConfigurationInitializerTest
{
    @Autowired ConfigurationDAO dao;


    @Test
    void defaultTimezoneIsInserted()
    {
        ConfigurationModel cfg = dao.findByKey("default.printing.timezone").orElseThrow();
        assertThat(cfg.getValue()).isEqualTo("GB");
    }
}
