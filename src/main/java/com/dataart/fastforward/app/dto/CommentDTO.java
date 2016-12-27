package com.dataart.fastforward.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by logariett on 29.11.2016.
 */
public class CommentDTO {

    @NotNull(message = "{error.comment.notnull}")
    @Size(min = 1, max = 500, message = "{error.comment.size}")
    private String commentText;

    public CommentDTO() {}

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
