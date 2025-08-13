package io.github.orionlibs.user.password.forgot.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ForgotPasswordRequestsDAOTest
{
    @Autowired ForgotPasswordRequestsDAO dao;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void findByForgotPasswordCode()
    {
        ForgotPasswordRequestModel model1 = new ForgotPasswordRequestModel();
        model1.setUserID(UUID.randomUUID());
        model1.setForgotPasswordCode("code1");
        model1.setExpiresAt(LocalDateTime.now().plusHours(1));
        model1 = dao.saveAndFlush(model1);
        ForgotPasswordRequestModel model2 = new ForgotPasswordRequestModel();
        model2.setUserID(UUID.randomUUID());
        model2.setForgotPasswordCode("code2");
        model2.setExpiresAt(LocalDateTime.now().plusHours(1));
        model2 = dao.saveAndFlush(model2);
        Optional<ForgotPasswordRequestModel> modelWrap = dao.findByForgotPasswordCode("code1");
        assertThat(modelWrap.get().getForgotPasswordCode()).isEqualTo("code1");
    }


    @Test
    void findAll()
    {
        ForgotPasswordRequestModel model1 = new ForgotPasswordRequestModel();
        model1.setUserID(UUID.randomUUID());
        model1.setForgotPasswordCode("code1");
        model1.setExpiresAt(LocalDateTime.now().plusHours(1));
        model1 = dao.saveAndFlush(model1);
        ForgotPasswordRequestModel model2 = new ForgotPasswordRequestModel();
        model2.setUserID(UUID.randomUUID());
        model2.setForgotPasswordCode("code2");
        model2.setExpiresAt(LocalDateTime.now().plusHours(1));
        model2 = dao.saveAndFlush(model2);
        List<ForgotPasswordRequestModel> models = dao.findAll();
        assertThat(models.get(0).getForgotPasswordCode()).isEqualTo("code1");
        assertThat(models.get(1).getForgotPasswordCode()).isEqualTo("code2");
    }


    @Test
    void deleteByForgotPasswordCode()
    {
        ForgotPasswordRequestModel model1 = new ForgotPasswordRequestModel();
        model1.setUserID(UUID.randomUUID());
        model1.setForgotPasswordCode("code1");
        model1.setExpiresAt(LocalDateTime.now().plusHours(1));
        model1 = dao.saveAndFlush(model1);
        ForgotPasswordRequestModel model2 = new ForgotPasswordRequestModel();
        model2.setUserID(UUID.randomUUID());
        model2.setForgotPasswordCode("code2");
        model2.setExpiresAt(LocalDateTime.now().plusHours(1));
        model2 = dao.saveAndFlush(model2);
        dao.deleteByForgotPasswordCode("code1");
        List<ForgotPasswordRequestModel> models = dao.findAll();
        assertThat(models.size()).isEqualTo(1);
        assertThat(models.get(0).getForgotPasswordCode()).isEqualTo("code2");
    }


    @Test
    void findUsersIDByForgotPasswordCode()
    {
        ForgotPasswordRequestModel model1 = new ForgotPasswordRequestModel();
        UUID userID1 = UUID.randomUUID();
        model1.setUserID(userID1);
        model1.setForgotPasswordCode("code1");
        model1.setExpiresAt(LocalDateTime.now().plusHours(1));
        model1 = dao.saveAndFlush(model1);
        ForgotPasswordRequestModel model2 = new ForgotPasswordRequestModel();
        UUID userID2 = UUID.randomUUID();
        model2.setUserID(userID2);
        model2.setForgotPasswordCode("code2");
        model2.setExpiresAt(LocalDateTime.now().plusHours(1));
        model2 = dao.saveAndFlush(model2);
        assertThat(dao.findUsersIDByForgotPasswordCode("code1")).isEqualTo(userID1.toString());
        assertThat(dao.findUsersIDByForgotPasswordCode("code2")).isEqualTo(userID2.toString());
    }


    @Test
    void deleteByExpiresAtBefore()
    {
        ForgotPasswordRequestModel model1 = new ForgotPasswordRequestModel();
        model1.setUserID(UUID.randomUUID());
        model1.setForgotPasswordCode("code1");
        model1.setExpiresAt(LocalDateTime.now().plusHours(1));
        model1 = dao.saveAndFlush(model1);
        ForgotPasswordRequestModel model2 = new ForgotPasswordRequestModel();
        model2.setUserID(UUID.randomUUID());
        model2.setForgotPasswordCode("code2");
        model2.setExpiresAt(LocalDateTime.now().plusHours(5));
        model2 = dao.saveAndFlush(model2);
        dao.deleteByExpiresAtBefore(LocalDateTime.now().plusHours(3));
        List<ForgotPasswordRequestModel> models = dao.findAll();
        assertThat(models.size()).isEqualTo(1);
        assertThat(models.get(0).getForgotPasswordCode()).isEqualTo("code2");
    }
}
