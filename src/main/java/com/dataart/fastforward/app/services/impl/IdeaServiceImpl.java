package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by logariett on 23.11.16.
 */
@Service
public class IdeaServiceImpl implements IdeaService {
    final static Logger logger = Logger.getLogger(IdeaServiceImpl.class);

    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private static UserService userRepository;

    @Override
    public void add(IdeaDTO ideaDTO) {
        Idea idea = new Idea();

        idea.setAuthor(ideaDTO.getAuthor());
        idea.setIdeaText(ideaDTO.getIdeaText());
        idea.setCreationDate(new Date());
        idea.setTags(ideaDTO.getTags());

        ideaRepository.saveAndFlush(idea);
    }

    @Override
    public void delete(long ideaId) {
        ideaRepository.delete(ideaId);}

    @Override
    public Idea edit(long ideaId, IdeaDTO ideaDTO) {
        Idea idea = ideaRepository.findOne(ideaId);

        if (!idea.getAuthor().equals(ideaDTO.getAuthor())) {
            String message = "Ideas author can not be changed";
            IllegalArgumentException ex = new IllegalArgumentException(message);
            logger.error(message,ex);
            throw ex;
        }

        if (idea.getIdeaText() == null
                || !idea.getIdeaText().equals(ideaDTO.getIdeaText()))
            idea.setIdeaText(ideaDTO.getIdeaText());

        if (idea.getUsersWhoBookmarked().size() == 0
                || !idea.getUsersWhoBookmarked().equals(ideaDTO.getUsersWhoBookmarked()))
            idea.setUsersWhoBookmarked(ideaDTO.getUsersWhoBookmarked());

        if (idea.getTags().size() == 0
                || !idea.getTags().equals(ideaDTO.getTags()))
            idea.setTags(ideaDTO.getTags());

        ideaRepository.saveAndFlush(idea);
        return idea;
    }

    @Override
    public Idea getIdeaById(long ideaId) {
        return ideaRepository.findOne(ideaId);}

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }
}
