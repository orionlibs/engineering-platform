package io.github.orionlibs.device.converter;

import io.github.orionlibs.core.Converter;
import io.github.orionlibs.device.api.DeviceDTO;
import io.github.orionlibs.device.model.DeviceModel;
import org.springframework.stereotype.Component;

@Component
public class DeviceModelToDTOConverter implements Converter<DeviceModel, DeviceDTO>
{
    @Override
    public DeviceDTO convert(DeviceModel objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        return new DeviceDTO(objectToConvert.getDeviceName(), objectToConvert.getDeviceDescription());
    }
}
