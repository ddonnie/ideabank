package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.dto.TagDTO;
import com.dataart.fastforward.app.model.Tag;
import com.dataart.fastforward.app.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        tagRepository.delete(tagId);
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
