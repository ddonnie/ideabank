package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.dto.MarkDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.MarkService;
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
    @Autowired
    private MarkService markService;

    @GetMapping
    public List<Idea> getAllIdeas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ideaService.getAll(username);
    }

    @PostMapping
    public Idea addIdea(@RequestBody IdeaDTO ideaDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ideaService.add(ideaDTO, username);
    }

    @GetMapping("/{ideaId}")
    public Idea getIdeaById(@PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ideaService.getIdeaById(ideaId, username);
    }

    @PutMapping("/{ideaId}")
    public Idea editIdea(@RequestBody IdeaDTO ideaDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ideaService.edit(ideaDTO, ideaId, username);
    }

    @DeleteMapping("/{ideaId}")
    public void deleteIdea(@PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        ideaService.delete(ideaId, username);
    }

    @PostMapping("/{ideaId}/vote")
    public Idea addVoteForIdea(@RequestBody MarkDTO markDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        markService.add(markDTO, ideaId, username);
        return ideaService.getIdeaById(ideaId, username);
    }

    @PutMapping("/{ideaId}/vote")
    public Idea editVoteForIdea(@RequestBody MarkDTO markDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        markService.edit(markDTO, ideaId, username);
        return ideaService.getIdeaById(ideaId, username);
    }
}
