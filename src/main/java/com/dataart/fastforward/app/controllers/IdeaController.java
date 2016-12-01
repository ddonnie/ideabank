package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Orlov on 23.11.2016.
 */

@RestController
@RequestMapping(value = "/ideas")
@ResponseStatus(HttpStatus.OK)
public class IdeaController {

    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private IdeaService ideaService;

    @GetMapping
    public List<Idea> getAllIdeas() {
        return ideaService.getAll();
    }

    @GetMapping("/{ideaId}")
    public Idea getIdeaById(@PathVariable long ideaId) {
        return ideaService.getIdeaById(ideaId);
    }

    @PostMapping
    public Idea addIdea(@RequestBody IdeaDTO ideaDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        return ideaService.add(ideaDTO, userName);
    }

    @PutMapping("/{ideaId}")
    public Idea editIdea(@RequestBody IdeaDTO ideaDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        return ideaService.edit(ideaDTO, ideaId, userName);
    }

    @DeleteMapping("/{ideaId}")
    public void deleteIdea(@PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        ideaService.delete(ideaId, userName);
    }
}
