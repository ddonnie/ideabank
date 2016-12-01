package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Orlov on 23.11.2016.
 */

@RestController
@RequestMapping(value = "/ideas")
public class IdeaController {

    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private IdeaService ideaService;

    @GetMapping
    public List<Idea> getAllIdeas() {
        return ideaService.getAll();
    }

    @GetMapping(value = "/{ideaId}")
    public Idea getIdeaById(@PathVariable long ideaId) {
        return ideaService.getIdeaById(ideaId);
    }

    @PostMapping(value = "/new_idea/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Idea addIdea(@RequestBody IdeaDTO ideaDTO, @PathVariable long userId) {
        return ideaService.add(ideaDTO, userId);
    }
}
