package com.dataart.fastforward.app.dto;

import com.dataart.fastforward.app.model.Account;

import java.util.Date;

/**
 * Created by logariett on 27.11.2016.
 */
public class IdeaDTO {

    private long ideaId;
    private Account author;
    private String ideaText;

    public IdeaDTO() {
    }

    public long getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(long ideaId) {
        this.ideaId = ideaId;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }
}
