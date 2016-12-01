package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by logariett on 23.11.16.
 */
public interface IdeaService {

    Idea add(IdeaDTO ideaDTO, long authorId);
    Idea edit(IdeaDTO ideaDTO, long ideaId);
    void delete(long ideaId);

    Idea getIdeaById(long ideaId);
    List<Idea> getAll();
}
