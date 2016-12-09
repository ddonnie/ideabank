package com.dataart.fastforward.app.dto;

/**
 * Created by logariett on 09.12.2016.
 */
public class AttachmentDTO {
    private String attachmentName;
    private String extension;
    private byte[] attachmentBytes;

    public AttachmentDTO() {
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
}
