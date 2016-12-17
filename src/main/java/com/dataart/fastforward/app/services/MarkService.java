package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.MarkDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Mark;
import com.dataart.fastforward.app.model.User;

import java.util.List;

/**
 * Created by logariett on 16.12.16.
 */
public interface MarkService {

    Mark add(MarkDTO markDTO, Idea idea, User user);
    Mark add(MarkDTO markDTO, long ideaId, String username);

    Mark edit(MarkDTO markDTO, Idea idea, User user);
    Mark edit(MarkDTO markDTO, long ideaId, String username);
    Mark edit(MarkDTO markDTO, Mark mark);

    void delete(Idea idea, User user);
    void delete(long ideaId, String username);
    void delete(Mark mark);
    void delete(Iterable<Mark> marks);

    Mark getMark(Idea idea, User user);
    Mark getMark(long ideaId, String username);

    List<Mark> getMarksByIdea(Idea idea);
    List<Mark> getMarksByIdea(long ideaId);

    List<Mark> getMarksByUser(User user);
    List<Mark> getMarksByUser(String username);
}
