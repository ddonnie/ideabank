package com.dataart.fastforward.app.services;

import com.dataart.fastforward.app.model.Idea;

/**
 * Created by Orlov on 20.12.2016.
 */
public interface AttachmentService {

    void add(String[] attachments, Idea idea);
    void delete(String attachmentName);

}
