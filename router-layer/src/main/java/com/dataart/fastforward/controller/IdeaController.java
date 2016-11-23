package com.dataart.fastforward.controller;

import com.dataart.fastforward.entity.Idea;
import com.dataart.fastforward.repository.IdeaRepository;
import com.dataart.fastforward.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Idea getIdeaById(@PathVariable long ideaId) {return ideaService.getIdeaById(ideaId);
    }

}
