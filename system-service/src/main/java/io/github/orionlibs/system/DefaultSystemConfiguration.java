package io.github.orionlibs.system;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class DefaultSystemConfiguration
{
    private Map<String, String> defaultConfig = new HashMap<>();


    public Map<String, String> getDefaultConfig()
    {
        return defaultConfig;
    }


    public void setDefaultConfig(Map<String, String> defaultConfig)
    {
        this.defaultConfig = defaultConfig;
    }
}
