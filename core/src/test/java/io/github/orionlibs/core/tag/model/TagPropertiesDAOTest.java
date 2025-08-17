package io.github.orionlibs.core.tag.model;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tag.DataType;
import io.github.orionlibs.core.tag.TagService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TagPropertiesDAOTest
{
    @Autowired TagPropertiesDAO dao;
    @Autowired TagService tagService;


    @BeforeEach
    void setup()
    {
        tagService.deleteAll();
        dao.deleteAll();
    }


    @Test
    void findAllByTag_Id()
    {
        TagModel tag = new TagModel();
        tag.setPath("tag/path/1");
        tag = tagService.save(tag);
        TagPropertyModel model1 = new TagPropertyModel();
        model1.setTag(tag);
        model1.setTagByPath(tag);
        model1.setName("prop1");
        model1.setValue("value1");
        model1.setDataType(DataType.STRING.getAsInt());
        model1 = dao.saveAndFlush(model1);
        TagPropertyModel model2 = new TagPropertyModel();
        model2.setTag(tag);
        model2.setTagByPath(tag);
        model2.setName("prop2");
        model2.setValue("value2");
        model2.setDataType(DataType.INTEGER.getAsInt());
        model2 = dao.saveAndFlush(model2);
        List<TagPropertyModel> models = dao.findAllByTag_Id(tag.getId());
        assertThat(models.size()).isEqualTo(2);
    }


    @Test
    void findAllByTagByPath_Path()
    {
        TagModel tag = new TagModel();
        tag.setPath("tag/path/1");
        tag = tagService.save(tag);
        TagPropertyModel model1 = new TagPropertyModel();
        model1.setTag(tag);
        model1.setTagByPath(tag);
        model1.setName("prop1");
        model1.setValue("value1");
        model1.setDataType(DataType.STRING.getAsInt());
        model1 = dao.saveAndFlush(model1);
        TagPropertyModel model2 = new TagPropertyModel();
        model2.setTag(tag);
        model2.setTagByPath(tag);
        model2.setName("prop2");
        model2.setValue("value2");
        model2.setDataType(DataType.INTEGER.getAsInt());
        model2 = dao.saveAndFlush(model2);
        List<TagPropertyModel> models = dao.findAllByTagByPath_Path("tag/path/1");
        assertThat(models.size()).isEqualTo(2);
    }
}
