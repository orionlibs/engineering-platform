package io.github.orionlibs.device;

import io.github.orionlibs.device.api.SaveDeviceRequest;
import io.github.orionlibs.device.converter.DeviceDTOToModelConverter;
import io.github.orionlibs.device.model.DeviceModel;
import io.github.orionlibs.device.model.DevicesDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService
{
    @Autowired private DevicesDAO dao;
    @Autowired private DeviceDTOToModelConverter deviceDTOToModelConverter;


    @Transactional(readOnly = true)
    public List<DeviceModel> getAll()
    {
        return dao.findAll();
    }


    @Transactional(readOnly = true)
    public Optional<DeviceModel> getById(String deviceID)
    {
        return dao.findById(UUID.fromString(deviceID));
    }


    @Transactional
    public DeviceModel save(SaveDeviceRequest request)
    {
        return dao.saveAndFlush(deviceDTOToModelConverter.convert(request));
    }


    @Transactional
    public DeviceModel update(DeviceModel model)
    {
        return dao.saveAndFlush(model);
    }
}
