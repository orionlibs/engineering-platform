package io.github.orionlibs.device.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DevicesDAOTest
{
    @Autowired DevicesDAO dao;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void findById()
    {
        DeviceModel model1 = new DeviceModel();
        model1.setDeviceName("device1");
        model1.setDeviceDescription("desc1");
        model1 = dao.saveAndFlush(model1);
        DeviceModel model2 = new DeviceModel();
        model2.setDeviceName("device2");
        model2.setDeviceDescription("desc2");
        model2 = dao.saveAndFlush(model2);
        Optional<DeviceModel> modelWrap = dao.findById(model1.getId());
        assertThat(modelWrap.get().getDeviceName()).isEqualTo("device1");
        assertThat(modelWrap.get().getDeviceDescription()).isEqualTo("desc1");
    }


    @Test
    void findAll()
    {
        DeviceModel model1 = new DeviceModel();
        model1.setDeviceName("device1");
        model1.setDeviceDescription("desc1");
        model1 = dao.saveAndFlush(model1);
        DeviceModel model2 = new DeviceModel();
        model2.setDeviceName("device2");
        model2.setDeviceDescription("desc2");
        model2 = dao.saveAndFlush(model2);
        List<DeviceModel> models = dao.findAll();
        assertThat(models.get(0).getDeviceName()).isEqualTo("device1");
        assertThat(models.get(0).getDeviceDescription()).isEqualTo("desc1");
        assertThat(models.get(1).getDeviceName()).isEqualTo("device2");
        assertThat(models.get(1).getDeviceDescription()).isEqualTo("desc2");
    }
}
