package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.User;

import java.util.Collection;
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

    Idea setMarkInfoForCurrUser(Idea idea, User loggedUser);
    Collection<Idea> setMarkInfoForCurrUser (Collection<Idea> ideas, User loggedUser);

    List<Comment> getAllComments(long ideaId);
    List<Attachment> getAllAttachments(long ideaId);

    void updateMarkCounters(Idea idea);
    void updateMarkCounters(long ideaId);

    void incrementMarkCounter(Idea idea, int mark);
    void incrementMarkCounter(long ideaId, int mark);

    void decrementMarkCounter(Idea idea, int mark);
    void decrementMarkCounter(long ideaId, int mark);

    void transferMark(Idea idea, int oldMark, int newMark);
    void transferMark(long ideaId, int oldMark, int newMark);
}
