package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.model.Idea;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by logariett on 23.11.16.
 */
public interface IdeaService {

    Idea add(Idea idea);
    Idea edit(Idea idea);
    void delete(long ideaId);

    Idea getIdeaById(long ideaId);
    List<Idea> getAll();
}
