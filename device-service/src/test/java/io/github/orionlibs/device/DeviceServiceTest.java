package io.github.orionlibs.device;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.device.api.SaveDeviceRequest;
import io.github.orionlibs.device.model.DeviceModel;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DeviceServiceTest
{
    @Autowired DeviceService deviceService;


    @Test
    void getNumberOfDevices()
    {
        DeviceModel model1 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build());
        DeviceModel model2 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device2")
                        .deviceDescription("desc2")
                        .build());
        assertThat(2).isEqualTo(deviceService.getNumberOfDevices());
    }


    @Test
    void getAll()
    {
        DeviceModel model1 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build());
        DeviceModel model2 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device2")
                        .deviceDescription("desc2")
                        .build());
        List<DeviceModel> result = deviceService.getAll();
        assertThat(result.get(0).getDeviceName()).isEqualTo("device1");
        assertThat(result.get(0).getDeviceDescription()).isEqualTo("desc1");
        assertThat(result.get(1).getDeviceName()).isEqualTo("device2");
        assertThat(result.get(1).getDeviceDescription()).isEqualTo("desc2");
    }


    @Test
    void getById()
    {
        DeviceModel model1 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build());
        DeviceModel model2 = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device2")
                        .deviceDescription("desc2")
                        .build());
        Optional<DeviceModel> result = deviceService.getById(model1.getId().toString());
        assertThat(result.get().getDeviceName()).isEqualTo("device1");
        assertThat(result.get().getDeviceDescription()).isEqualTo("desc1");
    }


    @Test
    void save()
    {
        DeviceModel model = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build());
        assertThat(model.getId().toString().length()).isGreaterThan(20);
    }


    @Test
    void update()
    {
        DeviceModel model = deviceService.save(SaveDeviceRequest.builder()
                        .deviceName("device1")
                        .deviceDescription("desc1")
                        .build());
        model.setDeviceName("device2");
        model.setDeviceDescription("desc2");
        deviceService.update(model);
        Optional<DeviceModel> result = deviceService.getById(model.getId().toString());
        assertThat(result.get().getDeviceName()).isEqualTo("device2");
        assertThat(result.get().getDeviceDescription()).isEqualTo("desc2");
    }
}
