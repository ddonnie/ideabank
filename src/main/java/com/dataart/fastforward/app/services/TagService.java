package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.TagDTO;
import com.dataart.fastforward.app.model.Tag;

import java.util.List;

/**
 * Created by logariett on 29.11.2016.
 */
public interface TagService {

    void add(TagDTO tagDTO);
    Tag edit(Tag tag);
    void delete(long tagId);

    Tag getTagById(long tagId);
    List<Tag> getAll();
}
