package com.dataart.fastforward.app.services.impl;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.dao.UserRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Tag;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.TagService;
import com.dataart.fastforward.app.services.UserService;
import com.dataart.fastforward.app.services.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.dataart.fastforward.app.services.validation.ValidationUtils.assertExistsNotBlank;

/**
 * Created by logariett on 23.11.16.
 */
@Service
public class IdeaServiceImpl implements IdeaService {

    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;

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
        for (String tagName : ideaDTO.getTags()) {
            tag = tagService.getTagByTagName(tagName);
            tag.getIdeasWithThisTag().add(idea);
            idea.getTags().add(tag);
            tagRepository.save(tag);
        }
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

        Set<User> users = idea.getUsersWhoBookmarked();
        for (User user : users) {
            user.getBookmarkedIdeas().remove(idea);
            userRepository.save(user);
        }
        
        userRepository.flush();
        ideaRepository.delete(ideaId);
    }

    @Override
    public Idea edit(IdeaDTO ideaDTO, long ideaId, String userName) {
        Idea idea = getIdeaById(ideaId);
        ValidationUtils.assertAuthor(idea, userService.getUserByUsername(userName));

        idea.setIdeaName(ideaDTO.getIdeaName());
        idea.setIdeaText(ideaDTO.getIdeaText());
        idea.setLastModifiedDate(new Date());

        ideaRepository.saveAndFlush(idea);
        return idea;
    }

    @Override
    public Idea getIdeaById(long ideaId) { return ideaRepository.findOne(ideaId);}

    @Override
    public List<Idea> getAll() {
        return ideaRepository.findAll();
    }
}
