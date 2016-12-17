package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.MarkRepository;
import com.dataart.fastforward.app.dto.MarkDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Mark;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.MarkService;
import com.dataart.fastforward.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by logariett on 16.12.16.
 */
@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private IdeaService ideaService;
    @Autowired
    private UserService userService;

    /* ********** add ********** */
    @Override
    @Transactional
    public Mark add(MarkDTO markDTO, Idea idea, User user) {
        Mark mark = new Mark();
        mark.setMarkId(new Mark.MarkPk(idea, user));
        mark.setMark(markDTO.getMark());
        mark = markRepository.saveAndFlush(mark);

        ideaService.updateMarkCounters(idea);

        return mark;
    }

//    call add(MarkDTO, Idea, User)
    @Override
    @Transactional
    public Mark add(MarkDTO markDTO, long ideaId, String username) {
        return add(markDTO, ideaService.getIdeaById(ideaId), userService.getUserByUsername(username));
    }

    /* ********** edit ********** */
    @Override
    @Transactional
    public Mark edit(MarkDTO markDTO, Idea idea, User user) {
        return edit(markDTO, markRepository.findOne(new Mark.MarkPk(idea, user)));
    }

    @Override
    @Transactional
    public Mark edit(MarkDTO markDTO, long ideaId, String username) {
        return edit(markDTO, markRepository.findOne(new Mark.MarkPk(ideaService.getIdeaById(ideaId), userService.getUserByUsername(username))));
    }

    @Override
    @Transactional
    public Mark edit(MarkDTO markDTO, Mark mark) {
        ideaService.transferMark(mark.getMarkId().getIdea(), mark.getMark(), markDTO.getMark());
        mark.setMark(markDTO.getMark());
        return markRepository.saveAndFlush(mark);
    }

    /* ********** delete ********** */

    @Override
    @Transactional
    public void delete(Iterable<Mark> marks) {
        for (Mark mark : marks)
            delete(mark);
    }

    @Override
    @Transactional
    public void delete(Mark mark) {
        ideaService.decrementMarkCounter(mark.getMarkId().getIdea(), mark.getMark());
        markRepository.delete(mark);
    }

    @Override
    @Transactional
    public void delete(Idea idea, User user) {
        delete(markRepository.findOne(new Mark.MarkPk(idea, user)));
    }

    @Override
    @Transactional
    public void delete(long ideaId, String username) {
        delete(markRepository.findOne(new Mark.MarkPk(ideaService.getIdeaById(ideaId), userService.getUserByUsername(username))));
    }

    /* ********** get one ********** */
    @Override
    public Mark getMark(Idea idea, User user) {
        return markRepository.findOne(new Mark.MarkPk(idea, user));
    }

    @Override
    @Transactional
    public Mark getMark(long ideaId, String username) {
        return markRepository.findOne(new Mark.MarkPk(ideaService.getIdeaById(ideaId), userService.getUserByUsername(username)));
    }

    /* ********** get multiple ********** */
    @Override
    public List<Mark> getMarksByIdea(Idea idea) {
        return markRepository.findByMarkIdIdea(idea);
    }

    @Override
    public List<Mark> getMarksByIdea(long ideaId) {
        return markRepository.findByMarkIdIdea_Idea(ideaId);
    }

    @Override
    public List<Mark> getMarksByUser(User user) {
        return null;
    }

    @Override
    public List<Mark> getMarksByUser(String username) {
        return null;
    }
}
