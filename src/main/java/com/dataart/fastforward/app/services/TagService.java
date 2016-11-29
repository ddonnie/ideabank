package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.TagDTO;
import com.dataart.fastforward.app.model.Tag;

import java.util.List;

/**
 * Created by logariett on 29.11.2016.
 */
public interface TagService {

    void add(TagDTO ideaDTO);
    Tag edit(Tag idea);
    void delete(long ideaId);

    Tag getIdeaById(long ideaId);
    List<Tag> getAll();
}
