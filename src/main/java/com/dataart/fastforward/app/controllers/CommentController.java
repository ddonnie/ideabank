package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.CommentRepository;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by logariett on 29.11.2016.
 */
@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAll();
    }

    @GetMapping(value = "/{commentId}")
    public Comment getCommentById(@PathVariable long commentId) {
        return commentService.getCommentById(commentId);
    }
}
