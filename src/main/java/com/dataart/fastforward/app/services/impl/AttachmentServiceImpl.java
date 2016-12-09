package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.AttachmentRepository;
import com.dataart.fastforward.app.dto.AttachmentDTO;
import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.services.AttachmentService;
import com.dataart.fastforward.app.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by logariett on 08.12.2016.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private IdeaService ideaService;

    @Override
    public Attachment add(AttachmentDTO attachmentDTO, long ideaId) {
        Attachment attachment = new Attachment();
        attachment.setIdea(ideaService.getIdeaById(ideaId));
        attachment.setAttachmentName(ideaId + "att" + new Date().getTime());
        attachment.setExtension(attachmentDTO.getExtension());
        attachment.setAttachmentBytes(attachmentDTO.getAttachmentBytes());
        return attachmentRepository.saveAndFlush(attachment);
    }

    @Override
    public void delete(long attachmentId) {
        attachmentRepository.delete(getAttachmentById(attachmentId));
    }

    @Override
    public Attachment getAttachmentById(long attachmentId) {
        return attachmentRepository.getOne(attachmentId);
    }

    @Override
    public Attachment getAttachmentByAttachmentName(String attachmentName) {
        return attachmentRepository.findAttachmentByAttachmentName(attachmentName);
    }
}
