package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.model.Idea;

import java.util.List;

/**
 * Created by logariett on 23.11.16.
 */
public interface IdeaService {

    Idea add(IdeaDTO ideaDTO, String userName);
    Idea edit(IdeaDTO ideaDTO, long ideaId, String userName);
    void delete(long ideaId, String userName);
    Idea getIdeaById(long ideaId);
    List<Idea> getAll();
    List<Comment> getAllComments(long ideaId);
}
