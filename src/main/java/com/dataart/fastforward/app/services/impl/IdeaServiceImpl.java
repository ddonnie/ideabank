package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.CommentRepository;
import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.dto.AttachmentDTO;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Tag;
import com.dataart.fastforward.app.services.AttachmentService;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.TagService;
import com.dataart.fastforward.app.services.UserService;
import com.dataart.fastforward.app.services.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.dataart.fastforward.app.services.validation.ValidationUtils.assertExistsNotBlank;

/**
 * Created by logariett on 23.11.16.
 */
@Service
public class IdeaServiceImpl implements IdeaService {

    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AttachmentService attachmentService;


    @Override
    public Idea add(IdeaDTO ideaDTO, String userName) {
        assertExistsNotBlank(ideaDTO);

        Idea idea = new Idea();

        idea.setAuthor(userService.getUserByUsername(userName));
        idea.setIdeaName(ideaDTO.getIdeaName());
        idea.setIdeaText(ideaDTO.getIdeaText());
        updateTagSet(idea, ideaDTO);
        idea.setCreationDate(new Date());

        idea = ideaRepository.saveAndFlush(idea);
        tagRepository.flush();
        updateAttachmentSet(idea, ideaDTO);
        return ideaRepository.saveAndFlush(idea);
    }

    @Override
    @Transactional
    public void delete(long ideaId, String userName) {
        Idea idea = ideaRepository.getOne(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        List<Long> tagsToDelete = updateTagSet(idea, null);
        List<Attachment> attachmentstoDelete = updateAttachmentSet(idea, null);
        for (Attachment attachment : attachmentstoDelete)
            attachmentService.delete(attachment);
        ideaRepository.delete(ideaId);

        for (Long tagId : tagsToDelete)
            tagService.delete(tagId);

    }

    @Override
    public Idea edit(IdeaDTO ideaDTO, long ideaId, String userName) {
        Idea idea = getIdeaById(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        idea.setIdeaName(ideaDTO.getIdeaName());
        idea.setIdeaText(ideaDTO.getIdeaText());
        List<Long> tagsToDelete = updateTagSet(idea, ideaDTO);
        List<Attachment> attachmentsToDelete = updateAttachmentSet(idea, ideaDTO);
        idea.setLastModifiedDate(new Date());

        idea = ideaRepository.saveAndFlush(idea);
        for (Long tagId : tagsToDelete)
            tagService.delete(tagId);
        for (Attachment attachment : attachmentsToDelete)
            attachmentService.delete(attachment);
        return idea;
    }

    @Override
    public Idea getIdeaById(long ideaId) {
        return ideaRepository.findOne(ideaId);
    }

    @Override
    public List<Comment> getAllComments(long ideaId) {
        return commentRepository.getAllCommentsByIdea(getIdeaById(ideaId));
    }

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }

    private List<Long> updateTagSet(Idea idea, IdeaDTO ideaDTO) {
        List<Long> tagsToDelete = new ArrayList<>(idea.getTags().size());

        if (ideaDTO == null || ideaDTO.getTags().length == 0) {
            for (Tag tag : idea.getTags()) {
                tag.getIdeasWithThisTag().remove(idea);

                if(tag.getIdeasWithThisTag().size() == 0)
                    tagsToDelete.add(tag.getTagId());
            }
            idea.getTags().clear();

        } else if (idea.getTags().size() == 0) {
            Tag tag;
            for (String tagToAdd : ideaDTO.getTags()) {
                if ((tag = tagService.getTagByTagName(tagToAdd)) == null)
                    tag = tagService.add(tagToAdd);

                idea.getTags().add(tag);
            }

        } else {
            List<String> tagsToRemove = new ArrayList(idea.getTags().size());
            List<String> tagsToAdd = new ArrayList<>(Arrays.asList(ideaDTO.getTags()));

            for (Tag tag : idea.getTags())
                tagsToRemove.add(tag.getTagName());

            tagsToAdd.removeAll(tagsToRemove);
            tagsToRemove.removeAll(Arrays.asList(ideaDTO.getTags()));

            Tag tag;
            for (String tagToRemove : tagsToRemove) {
                tag = tagService.getTagByTagName(tagToRemove);

                idea.getTags().remove(tag);
                tag.getIdeasWithThisTag().remove(idea);

                if (tag.getIdeasWithThisTag().size() == 0)
                    tagsToDelete.add(tag.getTagId());
            }
            for (String tagToAdd : tagsToAdd) {
                if ((tag = tagService.getTagByTagName(tagToAdd)) == null)
                    tag = tagService.add(tagToAdd);

                idea.getTags().add(tag);
            }
        }

        return tagsToDelete;
    }

    private List<Attachment> updateAttachmentSet(Idea idea, IdeaDTO ideaDTO) {
        List<Attachment> attachmentsToDelete = new ArrayList<>(idea.getAttachments().size());

        if(ideaDTO == null || ideaDTO.getAttachments().length == 0) {
            for (Attachment attachment : idea.getAttachments())
                attachmentsToDelete.add(attachment);
            idea.getAttachments().clear();
        } else if (idea.getAttachments().size() == 0) {
            for (AttachmentDTO attachmentDTO : ideaDTO.getAttachments()) {
                Attachment attachment = attachmentService.add(attachmentDTO,idea.getIdeaId());
                idea.getAttachments().add(attachment);
            }
        } else {
            AttachmentDTO[] attFromDto = Arrays.copyOf(ideaDTO.getAttachments(), ideaDTO.getAttachments().length);
            List<String> attNamesFromDTO = new ArrayList<>(ideaDTO.getAttachments().length);
            for (AttachmentDTO attachmentDTO : ideaDTO.getAttachments())
                attNamesFromDTO.add(attachmentDTO.getAttachmentName());

            List<Attachment> attachments = new ArrayList<>(idea.getAttachments());
            for(Attachment attachment : attachments)
                if (attNamesFromDTO.contains(attachment.getAttachmentName())) {
                    attFromDto[attNamesFromDTO.indexOf(attachment.getAttachmentName())] = null;
                } else {
                    idea.getAttachments().remove(attachment);
                    attachmentsToDelete.add(attachment);
                }

            for (AttachmentDTO attachmentDTO : attFromDto)
                if (attachmentDTO != null) {
                    idea.getAttachments().add(attachmentService.add(attachmentDTO,idea.getIdeaId()));
                }
        }

        return  attachmentsToDelete;
    }





































/*    private void updateAttachmentSet(Idea idea, IdeaDTO ideaDTO) {
        if (ideaDTO == null || ideaDTO.getAttachments().length == 0) {
            for (Attachment attachment : idea.getAttachments()) {
                attachmentService.delete(attachment.getAttachmentId());
            }
            idea.getAttachments().clear();

        } else if (idea.getAttachments().size() == 0) {
            Attachment attachment;
            for (AttachmentDTO attachmentDTO : ideaDTO.getAttachments()) {
                attachment = attachmentService.add(attachmentDTO,idea.getIdeaId());
                idea.getAttachments().add(attachment);
            }

        } else {
            List<String> attNamesToDelete = new ArrayList<>(idea.getAttachments().size());
            for(Attachment attachment : idea.getAttachments())
                attNamesToDelete.add(attachment.getAttachmentName());

            for(AttachmentDTO attachmentDTO : ideaDTO.getAttachments())
                attNamesToDelete.remove(attachmentDTO.getAttachmentName());

            for (String attachmentName : attNamesToDelete) {
                Attachment attachment = attachmentService.getAttachmentByAttachmentName(attachmentName);
                idea.getAttachments().remove(attachment);
                attachmentService.delete(attachment.getAttachmentId());
            }

            List<String> attNamesToRemain = new ArrayList<>(idea.getAttachments().size());
            for(Attachment attachment : idea.getAttachments())
                attNamesToRemain.add(attachment.getAttachmentName());
            for (AttachmentDTO attachmentDTO : ideaDTO.getAttachments())
                if (!attNamesToRemain.contains(attachmentDTO.getAttachmentName()))
                    idea.getAttachments().add(attachmentService.add(attachmentDTO, idea.getIdeaId()));
        }
    }*/
}
