package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Orlov on 23.11.2016.
 */

@RestController
@RequestMapping(value = "/ideas")
public class IdeaController {

    @Autowired
    IdeaRepository ideaRepository;
    @Autowired
    IdeaService ideaService;

    @GetMapping
    public List<Idea> getAllIdeas() {return ideaService.getAll();
    }

    @GetMapping(value = "/{ideaId}")
    public Idea getIdeaById(@PathVariable long ideaId) {return ideaService.getIdeaById(ideaId);}

}
