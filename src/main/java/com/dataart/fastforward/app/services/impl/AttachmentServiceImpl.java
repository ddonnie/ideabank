package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.AttachmentRepository;
import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * Created by Orlov on 20.12.2016.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    @Transactional
    public void add(MultipartFile ideaAttachment, Idea idea) {
        try {
            String uploadsDir = "/upload/";
            String filepath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadsDir);
            if (!new File(filepath).exists()) {
                new File(filepath).mkdir();
            }
            System.out.println("realPathtoUploads = " + filepath);
            String orgName = (new Date().getTime()) + ideaAttachment.getOriginalFilename();
            String filePath = filepath + "\\" + orgName;
            File dest = new File(filePath);
            ideaAttachment.transferTo(dest);

            Attachment attachment = new Attachment();
            attachment.setIdea(idea);
            attachment.setAttachmentName(orgName);
            attachmentRepository.saveAndFlush(attachment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(String attachmentName) {

        String uploadsDir = "/upload/";
        String filepath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadsDir);
        System.out.println("File to delete: "+filepath+attachmentName);
        File file = new File(filepath+attachmentName);
        file.delete();
    }
}
