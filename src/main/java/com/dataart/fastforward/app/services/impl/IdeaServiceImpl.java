package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.AccountService;
import com.dataart.fastforward.app.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by logariett on 23.11.16.
 */
@Service
public class IdeaServiceImpl implements IdeaService {

    @Autowired
    private IdeaRepository ideaRepository;

    @Override
    public void add(IdeaDTO ideaDTO) {
        Idea idea = new Idea();

        idea.setAuthor(ideaDTO.getAuthor());
        idea.setIdeaText(ideaDTO.getIdeaText());
        idea.setCreationDate(new Date());

        ideaRepository.saveAndFlush(idea);
    }

    @Override
    public void delete(long ideaId) { ideaRepository.delete(ideaId);}

    @Override
    public Idea edit(Idea idea) {return ideaRepository.saveAndFlush(idea);}

    @Override
    public Idea getIdeaById(long ideaId) { return ideaRepository.findOne(ideaId);}

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }
}
