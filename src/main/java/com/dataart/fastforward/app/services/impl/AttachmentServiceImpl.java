package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.AttachmentRepository;
import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Orlov on 20.12.2016.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public void add(String[] attachments, Idea idea) {
        for (String attachmentData: attachments) {
            Attachment attachment = new Attachment();
            attachment.setAttachmentData(attachmentData);
            attachment.setIdea(idea);
            attachmentRepository.save(attachment);
        }
        attachmentRepository.flush();
    }

    @Override
    public void delete(String attachmentName) {
    }
}
