package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.CommentRepository;
import com.dataart.fastforward.app.dto.CommentDTO;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.CommentService;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by logariett on 29.11.2016.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private IdeaService ideaService;

    @Override
    public void add(CommentDTO commentDTO, String username, long ideaId) {
        Comment comment = new Comment();
        comment.setAuthor(userService.getUserByUsername(username));
        comment.setCommentText(commentDTO.getCommentText());
//        comment.setIdeaId(ideaId);
        comment.setIdea(ideaService.getIdeaById(ideaId));
        comment.setCreationDate(new Date());
        commentRepository.saveAndFlush(comment);
    }


    @Override
    public Comment edit(CommentDTO commentDTO) {
        return null;
    }

    @Override
    public void delete(long commentId) {
        Comment comment = commentRepository.findOne(commentId);
        comment.getIdea().getComments().remove(comment);
        commentRepository.delete(commentId);
    }

    @Override
    public Comment getCommentById(long commentId) {
        return commentRepository.findOne(commentId);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }
}
