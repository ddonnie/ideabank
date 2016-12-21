package com.dataart.fastforward.app.dto;

/**
 * Created by logariett on 27.11.2016.
 */
public class IdeaDTO {

    private String ideaName;
    private String ideaText;
    private String[] tags;

    public IdeaDTO() {
    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }

    public String getIdeaName() {
        return ideaName;
    }

    public void setIdeaName(String ideaName) {
        this.ideaName = ideaName;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

}
