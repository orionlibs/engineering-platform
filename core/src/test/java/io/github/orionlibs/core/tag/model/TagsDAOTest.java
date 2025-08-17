package io.github.orionlibs.core.tag.model;

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
public class TagsDAOTest
{
    @Autowired TagsDAO dao;


    @BeforeEach
    void setup()
    {
        dao.deleteAll();
    }


    @Test
    void findById()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = dao.saveAndFlush(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = dao.saveAndFlush(model2);
        Optional<TagModel> modelWrap = dao.findById(model2.getId());
        assertThat(modelWrap.get().getPath()).isEqualTo("tag/path/2");
    }


    @Test
    void findAll()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = dao.saveAndFlush(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = dao.saveAndFlush(model2);
        List<TagModel> models = dao.findAll();
        assertThat(models.size()).isEqualTo(2);
    }


    @Test
    void findByPath()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = dao.saveAndFlush(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = dao.saveAndFlush(model2);
        Optional<TagModel> modelWrap = dao.findByPath("tag/path/2");
        assertThat(modelWrap.get().getPath()).isEqualTo("tag/path/2");
    }


    @Test
    void findPathByID()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = dao.saveAndFlush(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = dao.saveAndFlush(model2);
        assertThat(dao.findPathByID(model2.getId())).isEqualTo("tag/path/2");
    }


    @Test
    void findIDByPath()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = dao.saveAndFlush(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = dao.saveAndFlush(model2);
        assertThat(dao.findIDByPath("tag/path/2")).isEqualTo(model2.getId());
    }


    @Test
    void getNumberOfTags()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = dao.saveAndFlush(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = dao.saveAndFlush(model2);
        assertThat(dao.count()).isEqualTo(2);
    }
}
