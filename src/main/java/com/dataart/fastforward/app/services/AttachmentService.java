package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.dto.AttachmentDTO;
import com.dataart.fastforward.app.model.Attachment;

/**
 * Created by logariett on 08.12.2016.
 */
public interface AttachmentService {

    Attachment add(AttachmentDTO attachmentDTO, long ideaId);
    void delete(long attachmentId);

    Attachment getAttachmentById(long attachmentId);
    Attachment getAttachmentByAttachmentName(String attachmentName);
}
