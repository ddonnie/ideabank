package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.model.Idea;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Orlov on 20.12.2016.
 */
public interface AttachmentService {

    void add(MultipartFile ideaAttachment, Idea idea);
    void delete(String attachmentName);

}
