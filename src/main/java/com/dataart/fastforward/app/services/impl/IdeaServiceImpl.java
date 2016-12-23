package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.AttachmentRepository;
import com.dataart.fastforward.app.dao.CommentRepository;
import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.*;
import com.dataart.fastforward.app.services.*;
import com.dataart.fastforward.app.model.Attachment;
import com.dataart.fastforward.app.model.Comment;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Tag;
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
    private AttachmentRepository attachmentRepository;


    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MarkService markService;



    @Override
    @Transactional
    public Idea add(IdeaDTO ideaDTO, String userName) {
        assertExistsNotBlank(ideaDTO);

        Idea idea = new Idea();

        idea.setAuthor(userService.getUserByUsername(userName));
        if (ideaDTO.getIdeaName()!=null) {
            idea.setIdeaName(ideaDTO.getIdeaName());
        }
        else {
            idea.setIdeaName("Без имени");
        }
        idea.setIdeaText(ideaDTO.getIdeaText());
        updateTagSet(idea, ideaDTO);
        idea.setCreationDate(new Date());

        idea = ideaRepository.saveAndFlush(idea);
        tagRepository.flush();
        return ideaRepository.saveAndFlush(idea);
    }

    @Override
    @Transactional
    public Idea edit(IdeaDTO ideaDTO, long ideaId, String userName) {
        Idea idea = getIdeaById(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        idea.setIdeaName(ideaDTO.getIdeaName());
        idea.setIdeaText(ideaDTO.getIdeaText());
        List<Long> tagsToDelete = updateTagSet(idea, ideaDTO);
        idea.setLastModifiedDate(new Date());

        idea = ideaRepository.saveAndFlush(idea);
        for (Long tagId : tagsToDelete)
            tagService.delete(tagId);
        return idea;
    }

    @Override
    @Transactional
    public void delete(long ideaId, String userName) {
        Idea idea = ideaRepository.getOne(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        List<Long> tagsToDelete = updateTagSet(idea, null);
        ideaRepository.delete(ideaId);

        for (Long tagId : tagsToDelete)
            tagService.delete(tagId);
    }

    @Override
    public Idea getIdeaById(long ideaId) {
        return ideaRepository.findOne(ideaId);
    }

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }

    @Override
    @Transactional
    public Idea setMarkInfoForCurrUser(Idea idea, User loggedUser) {
        setUserRating(idea, loggedUser);
        return idea;
    }
    @Override
    @Transactional
    public Collection<Idea> setMarkInfoForCurrUser(Collection<Idea> ideas, User loggedUser) {
        for (Idea idea : ideas)
            setUserRating(idea, loggedUser);
        return ideas;
    }

    @Override
    @Transactional
    public List<Comment> getAllComments(long ideaId) {
        return commentRepository.getAllCommentsByIdea(getIdeaById(ideaId));
    }

    @Override
    public List<Attachment> getAllAttachments(long ideaId) {
        return attachmentRepository.getAllAttachmentsByIdea(getIdeaById(ideaId));
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

    private void setUserRating(Idea idea, User loggedUser) {
        Mark mark = markService.getMark(idea, loggedUser);
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
}
