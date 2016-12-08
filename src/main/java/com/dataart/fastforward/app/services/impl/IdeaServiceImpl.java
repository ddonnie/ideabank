package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.CommentRepository;
import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
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
    private UserService userService;
    @Autowired
    private TagService tagService;

    @Override
    public Idea add(IdeaDTO ideaDTO, String userName) {
        assertExistsNotBlank(ideaDTO);

        Idea idea = new Idea();
        Tag tag;

        idea.setAuthor(userService.getUserByUsername(userName));
        idea.setIdeaName(ideaDTO.getIdeaName());
        idea.setIdeaText(ideaDTO.getIdeaText());
        updateTagSet(idea, ideaDTO);
        idea.setCreationDate(new Date());

        ideaRepository.saveAndFlush(idea);
        tagRepository.flush();
        return idea;
    }

    @Override
    @Transactional
    public void delete(long ideaId, String userName) {
        Idea idea = ideaRepository.getOne(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        ideaRepository.delete(ideaId);
    }

    @Override
    public Idea edit(IdeaDTO ideaDTO, long ideaId, String userName) {
        Idea idea = getIdeaById(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        idea.setIdeaName(ideaDTO.getIdeaName());
        idea.setIdeaText(ideaDTO.getIdeaText());
        List<Tag> tagsToDelete = updateTagSet(idea, ideaDTO);
        idea.setLastModifiedDate(new Date());

        ideaRepository.saveAndFlush(idea);
        for (Tag tag : tagsToDelete)
            tagService.delete(tag.getTagId());
        return idea;
    }

    @Override
    public Idea getIdeaById(long ideaId) { return ideaRepository.findOne(ideaId);}

    @Override
    public List<Comment> getAllComments(long ideaId) {
        return commentRepository.getAllCommentsByIdeaId(ideaId);
    }

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }

    private List<Tag> updateTagSet(Idea idea, IdeaDTO ideaDTO) {
        List<Tag> tagsToDeleteFromRepository = new ArrayList<>(idea.getTags().size());

        List<String> tagsToRemove = new ArrayList(idea.getTags().size());
        List<String> tagsToAdd = new ArrayList<>(Arrays.asList(ideaDTO.getTags()));

        for (Tag tag : idea.getTags())
            tagsToRemove.add(tag.getTagName());

        tagsToAdd.removeAll(tagsToRemove);
        tagsToRemove.removeAll(Arrays.asList(ideaDTO.getTags()));

        Tag tag;
        for(String tagToRemove : tagsToRemove) {
            tag = tagService.getTagByTagName(tagToRemove);

            idea.getTags().remove(tag);
            tag.getIdeasWithThisTag().remove(idea);

            if (tag.getIdeasWithThisTag().size() == 0)
                tagsToDeleteFromRepository.add(tag);
        }
        for(String tagToAdd : tagsToAdd) {
            if ((tag = tagService.getTagByTagName(tagToAdd)) == null)
                tag = tagService.add(tagToAdd);

            idea.getTags().add(tag);
        }
        return tagsToDeleteFromRepository;
    }
}
