package com.dataart.fastforward.app.model;

import com.dataart.fastforward.app.model.Idea;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;

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

    @Column(name = "extension")
    private String extension;

    @Column(name="attachment_bytes")
    private byte[] attachmentBytes;

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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getAttachmentBytes() {
        return attachmentBytes;
    }

    public void setAttachmentBytes(byte[] attachmentBytes) {
        this.attachmentBytes = attachmentBytes;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attachment that = (Attachment) o;

        if (attachmentId != that.attachmentId) return false;
        if (attachmentName != null ? !attachmentName.equals(that.attachmentName) : that.attachmentName != null)
            return false;
        if (extension != null ? !extension.equals(that.extension) : that.extension != null) return false;
        return Arrays.equals(attachmentBytes, that.attachmentBytes);

    }

    @Override
    public int hashCode() {
        int result = (int) (attachmentId ^ (attachmentId >>> 32));
        result = 31 * result + (attachmentName != null ? attachmentName.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(attachmentBytes);
        return result;
    }
}
