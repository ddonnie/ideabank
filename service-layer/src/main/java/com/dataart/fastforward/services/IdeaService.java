package com.dataart.fastforward.services;

import com.dataart.fastforward.entity.Idea;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by logariett on 23.11.16.
 */
public interface IdeaService {
    @Autowired


    Idea add(Idea idea);
    void delete(long ideaId);
    Idea getIdeaById(long ideaId);
    Idea edit(Idea idea);
    List<Idea> getAll();
}
