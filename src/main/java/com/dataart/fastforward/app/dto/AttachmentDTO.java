package com.dataart.fastforward.app.dto;

/**
 * Created by Orlov on 20.12.2016.
 */
public class AttachmentDTO {

    private byte[] attachmentBytes;
    private String attachmentExtension;
    private String attachmentName;

    public byte[] getAttachmentBytes() {
        return attachmentBytes;
    }

    public void setAttachmentBytes(byte[] attachmentBytes) {
        this.attachmentBytes = attachmentBytes;
    }

    public String getAttachmentExtension() {
        return attachmentExtension;
    }

    public void setAttachmentExtension(String attachmentExtension) {
        this.attachmentExtension = attachmentExtension;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public AttachmentDTO() {
    }
}
