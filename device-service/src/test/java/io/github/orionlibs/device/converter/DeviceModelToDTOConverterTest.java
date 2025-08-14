package io.github.orionlibs.device.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.device.api.DeviceDTO;
import io.github.orionlibs.device.model.DeviceModel;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DeviceModelToDTOConverterTest
{
    @Test
    void convert()
    {
        DeviceModel temp = new DeviceModel();
        temp.setDeviceName("device1");
        temp.setDeviceDescription("desc1");
        DeviceModelToDTOConverter converter = new DeviceModelToDTOConverter();
        DeviceDTO result = converter.convert(temp);
        assertThat("device1").isEqualTo(result.deviceName());
        assertThat("desc1").isEqualTo(result.deviceDescription());
    }


    @Test
    void convert_null()
    {
        DeviceModelToDTOConverter converter = new DeviceModelToDTOConverter();
        DeviceDTO result = converter.convert(null);
        assertThat(result).isNull();
    }
}
