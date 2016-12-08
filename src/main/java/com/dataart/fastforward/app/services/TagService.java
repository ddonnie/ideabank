package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.TagDTO;
import com.dataart.fastforward.app.model.Tag;

import java.util.List;

/**
 * Created by logariett on 29.11.2016.
 */
public interface TagService {

    Tag add(String name);
    Tag add(TagDTO tagDTO);

    Tag edit(TagDTO tagDTO);
    void delete(long tagId);

    Tag getTagById(long tagDTO);
    Tag getTagByTagName(String tagName);
    List<Tag> getAll();
}
