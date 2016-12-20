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
    Idea getIdeaById(long ideaId, String username);

    List<Idea> getAll();
    List<Idea> getAll(String username);

    List<Comment> getAllComments(long ideaId);

    void updateMarkCounters(Idea idea);
    void updateMarkCounters(long ideaId);

    void incrementMarkCounter(Idea idea, int mark);
    void incrementMarkCounter(long ideaId, int mark);

    void decrementMarkCounter(Idea idea, int mark);
    void decrementMarkCounter(long ideaId, int mark);

    void transferMark(Idea idea, int oldMark, int newMark);
    void transferMark(long ideaId, int oldMark, int newMark);
}
