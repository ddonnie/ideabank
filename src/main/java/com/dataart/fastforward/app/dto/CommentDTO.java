package com.dataart.fastforward.app.dto;

import com.dataart.fastforward.app.model.Account;
import com.dataart.fastforward.app.model.Idea;

import java.util.Date;

/**
 * Created by logariett on 29.11.2016.
 */
public class CommentDTO {

    private Account author;
    private Idea idea;
    private String commentText;
    private Date creationDate;

    public CommentDTO() {
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
