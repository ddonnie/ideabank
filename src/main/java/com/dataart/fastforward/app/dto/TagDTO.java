package com.dataart.fastforward.app.dto;

/**
 * Created by logariett on 29.11.2016.
 */
public class TagDTO {

    private long tagId;
    private String tagName;

    public TagDTO() {
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
