package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.UserRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by logariett on 23.11.16.
 */
@Service
public class IdeaServiceImpl implements IdeaService {

    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public Idea add(IdeaDTO ideaDTO, long authorId) {
        Idea idea = new Idea();

        idea.setAuthor(userService.getUserById(authorId));
        idea.setIdeaText(ideaDTO.getIdeaText());
        idea.setCreationDate(new Date());

        ideaRepository.saveAndFlush(idea);
        return idea;
    }

    @Override
    @Transactional
    public void delete(long ideaId) {
        Idea idea = ideaRepository.getOne(ideaId);
        Set<User> users = idea.getUsersWhoBookmarked();
        for (User user : users) {
            user.getBookmarkedIdeas().remove(idea);
            userRepository.saveAndFlush(user);
        }

        ideaRepository.delete(ideaId);
    }

    @Override
    public Idea edit(IdeaDTO ideaDTO, long ideaId) {
        Idea idea = getIdeaById(ideaId);

        if (idea.getIdeaText() == null
                || !idea.getIdeaText().equals(ideaDTO.getIdeaText())) {
            idea.setIdeaText(ideaDTO.getIdeaText());
        }

        ideaRepository.saveAndFlush(idea);
        return idea;
    }

    @Override
    public Idea getIdeaById(long ideaId) { return ideaRepository.findOne(ideaId);}

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }
}
