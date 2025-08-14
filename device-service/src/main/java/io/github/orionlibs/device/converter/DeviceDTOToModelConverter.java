package io.github.orionlibs.device.converter;

import io.github.orionlibs.core.Converter;
import io.github.orionlibs.device.api.SaveDeviceRequest;
import io.github.orionlibs.device.model.DeviceModel;
import org.springframework.stereotype.Component;

@Component
public class DeviceDTOToModelConverter implements Converter<SaveDeviceRequest, DeviceModel>
{
    @Override
    public DeviceModel convert(SaveDeviceRequest objectToConvert)
    {
        if(objectToConvert == null)
        {
            return null;
        }
        DeviceModel model = new DeviceModel();
        model.setDeviceName(objectToConvert.getDeviceName());
        model.setDeviceDescription(objectToConvert.getDeviceDescription());
        return model;
    }
}
