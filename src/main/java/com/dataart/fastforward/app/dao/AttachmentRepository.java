package com.dataart.fastforward.app.dao;

import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.model.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by logariett on 22.11.2016.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> getAllAttachmentsByIdea(Idea idea);
    void deleteByAttachmentName(String attachmentName);
}
