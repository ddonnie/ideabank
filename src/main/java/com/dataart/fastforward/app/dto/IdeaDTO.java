package com.dataart.fastforward.app.dto;

import com.dataart.fastforward.app.model.User;

/**
 * Created by logariett on 27.11.2016.
 */
public class IdeaDTO {

    private long ideaId;
    private User author;
    private String ideaText;

    public IdeaDTO() {
    }

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }
}
