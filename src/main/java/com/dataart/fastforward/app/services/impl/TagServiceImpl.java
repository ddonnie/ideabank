package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.dto.TagDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Tag;
import com.dataart.fastforward.app.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by logariett on 29.11.2016.
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag add(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setTagName(tagDTO.getTagName());
        return tagRepository.saveAndFlush(tag);
    }

    @Override
    public Tag add(String name) {
        Tag tag = new Tag();
        tag.setTagName(name);
        return tagRepository.saveAndFlush(tag);
    }

    @Override
    public Tag edit(TagDTO tagDTO) {
        Tag tag = tagRepository.findByTagName(tagDTO.getTagName());

        tag.setTagName(tagDTO.getTagName());
        tagRepository.saveAndFlush(tag);
        return tag;
    }

    @Override
    public void delete(long tagId) {
        Tag tag = getTagById(tagId);
        for (Idea idea : tag.getIdeasWithThisTag())
            idea.getTags().remove(tag);
        tagRepository.delete(tagId);
    }

    @Override
    public void checkAndDeleteIfNonRequired(Tag tag) {
            if (tag.getIdeasWithThisTag().size() == 0)
                delete(tag.getTagId());
    }

    @Override
    public void checkAndDeleteIfNonRequired(Iterable<Tag> tags) {
        for (Tag tag : tags)
            checkAndDeleteIfNonRequired(tag);
    }

    @Override
    public Tag getTagById(long tagId) {
        return tagRepository.findOne(tagId);
    }

    @Override
    public Tag getTagByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
}
