package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dto.CommentDTO;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.CommentService;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Comment> getIdeaComments(@PathVariable long ideaId) {
        return ideaService.getAllComments(ideaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@Valid @RequestBody CommentDTO commentDTO, @PathVariable long ideaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        commentService.add(commentDTO, userName, ideaId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(name = "ideaId") long ideaId, @PathVariable(name = "commentId") long commentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User loggedUser = userService.getUserByUsername(userName);

        Comment comment = commentService.getCommentById(commentId);
        if (comment.getAuthor().getUsername().equals(userName) || "ADMIN".equals(loggedUser.getRole().getRoleName()))
            commentService.delete(commentId);
    }
}
