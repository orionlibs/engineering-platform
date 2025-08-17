package io.github.orionlibs.core.tag;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.orionlibs.core.tag.model.TagModel;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TagServiceTest
{
    @Autowired TagService tagService;


    @BeforeEach
    void setup()
    {
        tagService.deleteAll();
    }


    @Test
    void getByID()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = tagService.save(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = tagService.save(model2);
        Optional<TagModel> modelWrap = tagService.getByID(model2.getId().toString());
        assertThat(modelWrap.get().getPath()).isEqualTo("tag/path/2");
    }


    @Test
    void getAllTags()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = tagService.save(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = tagService.save(model2);
        List<TagModel> models = tagService.getAllTags();
        assertThat(models.size()).isEqualTo(2);
    }


    @Test
    void getByPath()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = tagService.save(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = tagService.save(model2);
        Optional<TagModel> modelWrap = tagService.getByPath("tag/path/2");
        assertThat(modelWrap.get().getPath()).isEqualTo("tag/path/2");
    }


    @Test
    void getPathByID()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = tagService.save(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = tagService.save(model2);
        assertThat(tagService.getPathByID(model2.getId().toString())).isEqualTo("tag/path/2");
    }


    @Test
    void getIDByPath()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = tagService.save(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = tagService.save(model2);
        assertThat(tagService.getIDByPath("tag/path/2")).isEqualTo(model2.getId());
    }


    @Test
    void getNumberOfTags()
    {
        TagModel model1 = new TagModel();
        model1.setPath("tag/path/1");
        model1 = tagService.save(model1);
        TagModel model2 = new TagModel();
        model2.setPath("tag/path/2");
        model2 = tagService.save(model2);
        assertThat(tagService.getNumberOfTags()).isEqualTo(2);
    }
}
