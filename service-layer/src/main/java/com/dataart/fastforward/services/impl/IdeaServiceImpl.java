package com.dataart.fastforward.services.impl;

import com.dataart.fastforward.entity.Idea;
import com.dataart.fastforward.repository.IdeaRepository;
import com.dataart.fastforward.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by logariett on 23.11.16.
 */
@Service
public class IdeaServiceImpl implements IdeaService {

    @Autowired
    private IdeaRepository ideaRepository;

    @Override
    public Idea add(Idea idea) {
        Idea savedIdea = ideaRepository.saveAndFlush(idea);
        return savedIdea;
    }

    @Override
    public void delete(long ideaId) { ideaRepository.delete(ideaId);}

    @Override
    public Idea getIdeaById(long ideaId) { return ideaRepository.findOne(ideaId);}

    @Override
    public Idea edit(Idea idea) {return ideaRepository.saveAndFlush(idea);}

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }
}
