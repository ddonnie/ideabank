package com.dataart.fastforward.app.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Orlov on 20.12.2016.
 */
public class AttachmentDTO {

    MultipartFile[] attachments;

    public MultipartFile[] getAttachments() {
        return attachments;
    }

    public void setAttachments(MultipartFile[] attachments) {
        this.attachments = attachments;
    }
}
