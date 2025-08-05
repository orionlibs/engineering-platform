package io.github.orionlibs.system;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.system.configuration.ConfigurationService;
import io.github.orionlibs.system.configuration.model.ConfigurationModel;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ConfigurationServiceTest
{
    @Autowired ConfigurationService configurationService;


    @Test
    void getAll_defaultConfiguration()
    {
        List<ConfigurationModel> result = configurationService.getAll();
        Set<ConfigurationModel> expected = Set.of(new ConfigurationModel("default.printing.timezone", "GB"),
                        new ConfigurationModel("default.page.size", "50"));
        assertThat(expected.containsAll(result)).isTrue();
    }


    @Test
    void findByKey()
    {
        Optional<ConfigurationModel> result = configurationService.findByKey("default.printing.timezone");
        assertThat(result.get().getValue()).isEqualTo("GB");
    }


    @Test
    void existsByKey()
    {
        boolean result = configurationService.existsByKey("default.printing.timezone");
        assertThat(result).isTrue();
    }


    @Test
    void update()
    {
        configurationService.update("default.printing.timezone", "US");
        Optional<ConfigurationModel> result = configurationService.findByKey("default.printing.timezone");
        assertThat(result.get().getValue()).isEqualTo("US");
        configurationService.update("default.printing.timezone", "GB");
    }
}
