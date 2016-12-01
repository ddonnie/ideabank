package com.dataart.fastforward.service;

import com.dataart.fastforward.app.dao.IdeaRepository;
import com.dataart.fastforward.app.dao.UserRepository;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.User;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.UserService;
import com.dataart.fastforward.config.root.DbConfig;
import com.dataart.fastforward.config.servlet.WebMvcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

/**
 * Created by logariett on 01.12.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={DbConfig.class, WebMvcConfig.class})
public class IdeaServiceTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;
    @Autowired
    private IdeaService ideaService;

    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void addTest() {
        IdeaDTO ideaDTO = new IdeaDTO();
        ideaDTO.setIdeaText("where is your MIND");
        Idea idea = ideaService.add(ideaDTO, 2);

        User user = userService.getUserById(1);
        user.getBookmarkedIdeas().add(idea);
        idea.getUsersWhoBookmarked().add(user);
        ideaRepository.saveAndFlush(idea);
        userRepository.saveAndFlush(user);
    }

    @Test
    public void editTest() {
        IdeaDTO ideaDTO = new IdeaDTO();
        Idea idea = ideaService.getIdeaById(4);
        ideaDTO.setIdeaText(idea.getIdeaText() + "... sir?");
        ideaService.edit(ideaDTO, 4);
    }

    @Test
    public void deleteTest() {
        ideaService.delete(5);
    }
}
