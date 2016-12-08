package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dto.CommentDTO;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.services.CommentService;
import com.dataart.fastforward.app.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by logariett on 29.11.2016.
 */
@RestController
@RequestMapping(value = "/ideas/{ideaId}/comments")
public class CommentController {

    @Autowired
    private IdeaService ideaService;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getIdeaComments(@PathVariable long ideaId) {
        return ideaService.getAllComments(ideaId);
    }

    @PostMapping
    public void addComment(@RequestBody CommentDTO commentDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        commentService.add(commentDTO, userName, ideaId);
    }
}
