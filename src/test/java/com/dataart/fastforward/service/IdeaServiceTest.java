package com.dataart.fastforward.service;

import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Tag;
import com.dataart.fastforward.app.services.IdeaService;
import com.dataart.fastforward.app.services.TagService;
import com.dataart.fastforward.app.services.UserService;
import com.dataart.fastforward.config.root.DbConfig;
import com.dataart.fastforward.config.servlet.WebMvcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by logariett on 30.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={DbConfig.class, WebMvcConfig.class})
public class IdeaServiceTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserService userService;
    @Autowired
    private IdeaService ideaService;
    @Autowired
    private TagService tagService;

    @Test
    public void editDifferentAuthorTest() {
        Idea idea = ideaService.getIdeaById(1);

        IdeaDTO ideaDTO = new IdeaDTO();
        ideaDTO.setAuthor(userService.getUserById(2));
        ideaDTO.setIdeaText(idea.getIdeaText());
        ideaDTO.setUsersWhoBookmarked(idea.getUsersWhoBookmarked());
        ideaDTO.setTags(idea.getTags());

        System.out.println(ideaService.edit(1, ideaDTO));
    }

    @Test
    public void editDifferentTextTest() {
        Idea idea = ideaService.getIdeaById(1);

        IdeaDTO ideaDTO = new IdeaDTO();
        ideaDTO.setAuthor(idea.getAuthor());
        ideaDTO.setIdeaText(idea.getIdeaText() + "... no really, where");
        ideaDTO.setUsersWhoBookmarked(idea.getUsersWhoBookmarked());
        ideaDTO.setTags(idea.getTags());

        System.out.println(ideaService.edit(1, ideaDTO));
    }

    @Test
    public void editDifferentTagsTest() {
        Idea idea = ideaService.getIdeaById(1);

        IdeaDTO ideaDTO = new IdeaDTO();
        ideaDTO.setAuthor(idea.getAuthor());
        ideaDTO.setIdeaText(idea.getIdeaText());
        ideaDTO.setUsersWhoBookmarked(idea.getUsersWhoBookmarked());

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Tag tag = tagService.getTagById(1);
        ideaDTO.getTags().add(tag);
        tag.getIdeasWithThisTag().add(idea);

        entityManager.merge(ideaService.edit(1, ideaDTO));
        entityManager.merge(tag);

        entityManager.flush();
//        System.out.println(ideaService.edit(1, ideaDTO));
    }
}
