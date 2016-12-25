package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.dto.MarkDTO;
import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.AttachmentService;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.MarkService;
import com.dataart.fastforward.app.services.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Orlov on 23.11.2016.
 */

@RestController
@RequestMapping(value = "/ideas")
@ResponseStatus(HttpStatus.OK)
public class IdeaController {

    @Autowired
    private IdeaService ideaService;
    @Autowired
    private UserService userService;
    @Autowired
    private MarkService markService;
    @Autowired
    private AttachmentService attachmentService;

    @GetMapping
    public List<Idea> getAllIdeas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User loggedUser = userService.getUserByUsername(username);

        List<Idea> ideas = ideaService.getAll();
        ideaService.setInfoForCurrUser(ideas, loggedUser);
        return ideas;
    }

    @PostMapping
    public void addIdea(
            @RequestParam(value = "ideaDTO") String incomingIdeaDTO,
            @RequestParam(value = "ideaAttachments") MultipartFile[] ideaAttachments)
            throws JsonMappingException, JsonParseException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("before"+incomingIdeaDTO.toString());
        IdeaDTO ideaDTO = new ObjectMapper().readValue(incomingIdeaDTO, IdeaDTO.class);
        Idea idea = ideaService.add(ideaDTO, username);
        for (MultipartFile ideaAttachment : ideaAttachments) {
            System.out.println(ideaAttachment.getClass());
            attachmentService.add(ideaAttachment, idea);
        }
    }

    @GetMapping("/{ideaId}")
    public Idea getIdeaById(@PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User loggedUser = userService.getUserByUsername(username);

        Idea idea = ideaService.getIdeaById(ideaId);
        ideaService.setInfoForCurrUser(idea, loggedUser);
        return idea;
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

        for (Attachment attachment : ideaService.getIdeaById(ideaId).getAttachments()) {
            attachmentService.delete(attachment.getAttachmentName());
        }
        ideaService.delete(ideaId, username);
    }


    @PostMapping("/{ideaId}/bookmark")
    public void addToBookmarks(@PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User loggedUser = userService.getUserByUsername(username);
        Idea idea = ideaService.getIdeaById(ideaId);

        userService.addBookmark(idea,loggedUser);
    }

    @DeleteMapping("/{ideaId}/bookmark")
    public void removeFromBookmarks(@PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User loggedUser = userService.getUserByUsername(username);
        Idea idea = ideaService.getIdeaById(ideaId);

        userService.deleteBookmark(idea,loggedUser);
    }

    @PostMapping("/{ideaId}/vote")
    public void addVoteForIdea(@RequestBody MarkDTO markDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        markService.add(markDTO, ideaId, username);
//        return ideaService.getIdeaById(ideaId, username);
    }

    @PutMapping("/{ideaId}/vote")
    public void editVoteForIdea(@RequestBody MarkDTO markDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        markService.edit(markDTO, ideaId, username);
//        return ideaService.getIdeaById(ideaId, username);
    }

    @DeleteMapping("/{ideaId}/vote")
    public void deleteVoteForIdea(@PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        markService.delete(ideaId, username);
//        return ideaService.getIdeaById(ideaId, username);
    }
}
