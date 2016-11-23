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
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }
}
