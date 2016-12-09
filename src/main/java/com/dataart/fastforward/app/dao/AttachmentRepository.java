package com.dataart.fastforward.app.dao;

import com.dataart.fastforward.app.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by logariett on 22.11.2016.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findAttachmentByAttachmentName(String name);
}
