package com.dataart.fastforward.app.dto;

import com.dataart.fastforward.app.model.Tag;
import com.dataart.fastforward.app.model.User;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by logariett on 27.11.2016.
 */
public class IdeaDTO {

    private User author;
    private String ideaText;
    private Set<User> usersWhoBookmarked;
    private Set<Tag> tags = new TreeSet<>();

    public IdeaDTO() {
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }

    public Set<User> getUsersWhoBookmarked() {
        return usersWhoBookmarked;
    }

    public void setUsersWhoBookmarked(Set<User> usersWhoBookmarked) {
        this.usersWhoBookmarked = usersWhoBookmarked;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }


}
