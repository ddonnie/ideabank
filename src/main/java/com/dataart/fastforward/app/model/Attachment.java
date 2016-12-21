package com.dataart.fastforward.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by logariett on 22.11.2016.
 */

@Entity
@Table(name = "Attachments")
public class Attachment {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "attachment_id", length = 6, nullable = false)
    private long attachmentId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)//, cascade = CascadeType.ALL)
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

    @Column(name = "attachment_name")
    private String attachmentName;

    public Attachment() {}

    public long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

}
