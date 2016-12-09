package com.dataart.fastforward.app.controllers;

import com.dataart.fastforward.app.dao.TagRepository;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.Tag;
import com.dataart.fastforward.app.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by logariett on 29.11.2016.
 */

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAll();
    }

    @GetMapping(value = "/{tagName}")
    public Set<Idea> getTagByName(@PathVariable String tagName) {
        return tagService.getTagByTagName(tagName).getIdeasWithThisTag();
    }
}
