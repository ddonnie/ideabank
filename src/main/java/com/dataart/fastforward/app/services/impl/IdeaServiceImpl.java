package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.CommentRepository;
import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.MarkRepository;
import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.dto.AttachmentDTO;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.*;
import com.dataart.fastforward.app.services.*;
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
    @Autowired
    private MarkService markService;


    @Override
    @Transactional
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
//        updateAttachmentSet(idea, ideaDTO);
        return ideaRepository.saveAndFlush(idea);
    }

    @Override
    @Transactional
    public void delete(long ideaId, String userName) {
        Idea idea = ideaRepository.getOne(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        List<Long> tagsToDelete = updateTagSet(idea, null);
        markService.delete(markService.getMarksByIdea(idea));
//        List<Attachment> attachmentstoDelete = updateAttachmentSet(idea, null);
//        for (Attachment attachment : attachmentstoDelete)
//            attachmentService.delete(attachment);
        ideaRepository.delete(ideaId);

        for (Long tagId : tagsToDelete)
            tagService.delete(tagId);

    }

    @Override
    @Transactional
    public Idea edit(IdeaDTO ideaDTO, long ideaId, String userName) {
        Idea idea = getIdeaById(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        idea.setIdeaName(ideaDTO.getIdeaName());
        idea.setIdeaText(ideaDTO.getIdeaText());
        List<Long> tagsToDelete = updateTagSet(idea, ideaDTO);
//        List<Attachment> attachmentsToDelete = updateAttachmentSet(idea, ideaDTO);
        idea.setLastModifiedDate(new Date());

        idea = ideaRepository.saveAndFlush(idea);
        for (Long tagId : tagsToDelete)
            tagService.delete(tagId);
//        for (Attachment attachment : attachmentsToDelete)
//            attachmentService.delete(attachment);
        return idea;
    }

    @Override
    @Transactional
    public Idea getIdeaById(long ideaId, String username) {
        Idea idea = ideaRepository.findOne(ideaId);
        setUserRating(idea, username);
        return idea;
    }

    @Override
    public Idea getIdeaById(long ideaId) {
        return ideaRepository.findOne(ideaId);
    }

    @Override
    @Transactional
    public List<Comment> getAllComments(long ideaId) {
        return commentRepository.getAllCommentsByIdea(getIdeaById(ideaId));
    }

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }

    @Override
    @Transactional
    public List<Idea> getAll(String username) {
        List<Idea> ideas = ideaRepository.findAll();
        for (Idea idea : ideas)
            setUserRating(idea, username);
        return ideas;
    }

    @Override
    @Transactional
    public void updateMarkCounters(Idea idea) {
        List<Mark> marks = markService.getMarksByIdea(idea);
        int upVotes = 0;
        int downVotes = 0;

        for (Mark mark : marks)
            if (mark.getMark() == 1)
                upVotes++;
            else if (mark.getMark() == -1)
                downVotes++;

        idea.setUpVoteCount(upVotes);
        idea.setDownVoteCount(downVotes);
        ideaRepository.saveAndFlush(idea);
    }

    @Override
    @Transactional
    public void updateMarkCounters(long ideaId) {
        Idea idea = ideaRepository.findOne(ideaId);
        updateMarkCounters(idea);
    }

    @Override
    @Transactional
    public void incrementMarkCounter(Idea idea, int mark) {
        if (mark == 1)
            idea.setUpVoteCount(idea.getUpVoteCount() + 1);
        else if (mark == -1)
            idea.setDownVoteCount(idea.getDownVoteCount() + 1);
        ideaRepository.saveAndFlush(idea);
    }

    @Override
    @Transactional
    public void incrementMarkCounter(long ideaId, int mark) {
        incrementMarkCounter(ideaRepository.getOne(ideaId), mark);
    }

    @Override
    @Transactional
    public void decrementMarkCounter(Idea idea, int mark) {
        if (mark == 1)
            idea.setUpVoteCount(idea.getUpVoteCount() - 1);
        else if (mark == -1)
            idea.setDownVoteCount(idea.getDownVoteCount() - 1);
        ideaRepository.saveAndFlush(idea);
    }

    @Override
    @Transactional
    public void decrementMarkCounter(long ideaId, int mark) {
        decrementMarkCounter(ideaRepository.getOne(ideaId), mark);
    }

    @Override
    @Transactional
    public void transferMark(Idea idea, int oldMark, int newMark) {
        decrementMarkCounter(idea, oldMark);
        incrementMarkCounter(idea, newMark);
    }

    @Override
    public void transferMark(long ideaId, int oldMark, int newMark) {
        transferMark(ideaRepository.getOne(ideaId), oldMark, newMark);
    }

    /* tekhnicheskie funkcii jeee */

    private void setUserRating(Idea idea, String username) {
        Mark mark = markService.getMark(idea, userService.getUserByUsername(username));
        idea.setUserMark(mark == null ? 0 : mark.getMark());
    }

    private List<Long> updateTagSet(Idea idea, IdeaDTO ideaDTO) {
        List<Long> tagsToDelete = new ArrayList<>(idea.getTags().size());

        if (ideaDTO == null || ideaDTO.getTags().length == 0) {
            for (Tag tag : idea.getTags()) {
                tag.getIdeasWithThisTag().remove(idea);

                if (tag.getIdeasWithThisTag().size() == 0)
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

/*    private List<Attachment> updateAttachmentSet(Idea idea, IdeaDTO ideaDTO) {
        List<Attachment> attachmentsToDelete = new ArrayList<>(idea.getAttachments().size());

        if (ideaDTO == null || ideaDTO.getAttachments().length == 0) {
            for (Attachment attachment : idea.getAttachments())
                attachmentsToDelete.add(attachment);
            idea.getAttachments().clear();
        } else if (idea.getAttachments().size() == 0) {
            for (AttachmentDTO attachmentDTO : ideaDTO.getAttachments()) {
                Attachment attachment = attachmentService.add(attachmentDTO, idea.getIdeaId());
                idea.getAttachments().add(attachment);
            }
        } else {
            AttachmentDTO[] attFromDto = Arrays.copyOf(ideaDTO.getAttachments(), ideaDTO.getAttachments().length);
            List<String> attNamesFromDTO = new ArrayList<>(ideaDTO.getAttachments().length);
            for (AttachmentDTO attachmentDTO : ideaDTO.getAttachments())
                attNamesFromDTO.add(attachmentDTO.getAttachmentName());

            List<Attachment> attachments = new ArrayList<>(idea.getAttachments());
            for (Attachment attachment : attachments)
                if (attNamesFromDTO.contains(attachment.getAttachmentName())) {
                    attFromDto[attNamesFromDTO.indexOf(attachment.getAttachmentName())] = null;
                } else {
                    idea.getAttachments().remove(attachment);
                    attachmentsToDelete.add(attachment);
                }

            for (AttachmentDTO attachmentDTO : attFromDto)
                if (attachmentDTO != null) {
                    idea.getAttachments().add(attachmentService.add(attachmentDTO, idea.getIdeaId()));
                }
        }

        return attachmentsToDelete;
    }*/
}
