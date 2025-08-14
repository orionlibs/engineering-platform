package io.github.orionlibs.device.converter;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.device.api.SaveDeviceRequest;
import io.github.orionlibs.device.model.DeviceModel;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DeviceDTOToModelConverterTest
{
    @Test
    void convert()
    {
        SaveDeviceRequest temp = new SaveDeviceRequest();
        temp.setDeviceName("device1");
        temp.setDeviceDescription("desc1");
        DeviceDTOToModelConverter converter = new DeviceDTOToModelConverter();
        DeviceModel result = converter.convert(temp);
        assertThat("device1").isEqualTo(result.getDeviceName());
        assertThat("desc1").isEqualTo(result.getDeviceDescription());
    }


    @Test
    void convert_null()
    {
        DeviceDTOToModelConverter converter = new DeviceDTOToModelConverter();
        DeviceModel result = converter.convert(null);
        assertThat(result).isNull();
    }
}
