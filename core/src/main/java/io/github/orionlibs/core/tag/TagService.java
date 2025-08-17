package io.github.orionlibs.core.tag;

import io.github.orionlibs.core.tag.model.TagModel;
import io.github.orionlibs.core.tag.model.TagsDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService
{
    @Autowired private TagsDAO dao;


    @Transactional(readOnly = true)
    public Optional<TagModel> getByID(String tagID)
    {
        return dao.findById(UUID.fromString(tagID));
    }


    @Transactional(readOnly = true)
    public Optional<TagModel> getByPath(String tagPath)
    {
        return dao.findByPath(tagPath);
    }


    @Transactional(readOnly = true)
    public String getPathByID(String tagID)
    {
        return dao.findPathByID(UUID.fromString(tagID));
    }


    @Transactional(readOnly = true)
    public UUID getIDByPath(String tagPath)
    {
        return dao.findIDByPath(tagPath);
    }


    @Transactional(readOnly = true)
    public long getNumberOfTags()
    {
        return dao.count();
    }


    @Transactional(readOnly = true)
    public List<TagModel> getAllTags()
    {
        return dao.findAll();
    }


    @Transactional
    public TagModel save(TagModel model)
    {
        return dao.saveAndFlush(model);
    }


    @Transactional
    public TagModel update(TagModel model)
    {
        return dao.saveAndFlush(model);
    }


    @Transactional
    public void deleteAll()
    {
        dao.deleteAll();
    }
}
