package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.CommentRepository;
import com.dataart.fastforward.app.dto.CommentDTO;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.services.CommentService;
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

    @Override
    public void add(CommentDTO commentDTO) {
        Comment comment = new Comment();

        comment.setAuthor(commentDTO.getAuthor());
        comment.setIdea(commentDTO.getIdea());
        comment.setCommentText(commentDTO.getCommentText());
        comment.setCreationDate(new Date());

        commentRepository.saveAndFlush(comment);
    }

    @Override
    public Comment edit(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public void delete(long commentId) {
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
