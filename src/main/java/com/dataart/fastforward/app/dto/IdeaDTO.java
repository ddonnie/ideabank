package com.dataart.fastforward.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logariett on 27.11.2016.
 */
public class IdeaDTO {

    private String ideaName;
    private String ideaText;
    private String[] tags;
    private AttachmentDTO[] attachments;

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

    public AttachmentDTO[] getAttachments() {
        return attachments;
    }

    public void setAttachments(AttachmentDTO[] attachments) {
        this.attachments = attachments;
    }
}
